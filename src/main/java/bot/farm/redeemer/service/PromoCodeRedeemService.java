package bot.farm.redeemer.service;

import bot.farm.redeemer.dto.ApiResponse;
import bot.farm.redeemer.entity.IggAccount;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

/**
 * Service class for redeeming promotional codes on a list of IggAccounts.
 * It interacts with an external service to redeem the promo code for each account.
 */
@Service
@RequiredArgsConstructor
public class PromoCodeRedeemService {
  private final ObjectMapper objectMapper;
  private final PromoCodeService promoCodeService;
  private static final String URL = "https://dut.igg.com/event/code?lang=";

  /**
   * Redeems a promotional code on a list of IggAccounts.
   *
   * @param promoCode The promotional code to redeem.
   * @param accounts  The list of IggAccounts on which the promo code will be redeemed.
   * @return A string containing the result of the redemption process.
   */
  public String redeemPromoCode(String promoCode, List<IggAccount> accounts) {
    List<String> activatedIds = new ArrayList<>();
    Map<String, String> othersIds = new HashMap<>();
    String trouble = "";

    try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

      Collections.shuffle(accounts);
      for (IggAccount ia : accounts) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("iggid", String.valueOf(ia.getIggId())));
        params.add(new BasicNameValuePair("cdkey", promoCode));
        params.add(new BasicNameValuePair("username", ""));
        params.add(new BasicNameValuePair("sign", "0"));

        HttpPost httpPost = new HttpPost(URL + ia.getLang());
        httpPost.setEntity(new UrlEncodedFormEntity(params));

        HttpResponse response = httpclient.execute(httpPost);

        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity);
        ApiResponse apiResponse = objectMapper.readValue(responseString, ApiResponse.class);

        // Если промокод истек или уже был применен на аккаунты, то цикл завершается досрочно
        if (apiResponse.msg().endsWith("[-57]") || apiResponse.msg().endsWith("[-51]")) {
          activatedIds.clear();
          othersIds.clear();
          if (apiResponse.msg().endsWith("[-51]")) {
            trouble = "Такого промокода не существует";
          } else if (apiResponse.msg().endsWith("[-57]")) {
            trouble = "Время действия промокода истекло";
          }
          break;
        }

        if (apiResponse.msg().endsWith("[-54]")) {
          othersIds.put(String.valueOf(ia.getIggId()), "Промокод был применен самостоятельно");
        } else if (apiResponse.code() != 0) {
          othersIds.put(String.valueOf(ia.getIggId()), apiResponse.msg());
        } else {
          activatedIds.add(String.valueOf(ia.getIggId()));
        }

        // Не забываем освободить ресурсы
        EntityUtils.consume(entity);
      }

      if (!activatedIds.isEmpty()) {
        // Сохраняем примененный промокод в бд, чтобы избежать повторных попыток активации
        promoCodeService.savePromoCode(promoCode);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return prepareResponseAfterRedeeming(activatedIds, othersIds, trouble);
  }

  /**
   * Prepares the response after redeeming the promotional code.
   *
   * @param activated A list of IGG IDs that were successfully activated.
   * @param others    A map of IGG IDs and reasons for which the promo code was not applied.
   * @param trouble   A string describing the reason for early completion.
   * @return A string containing the detailed response after redeeming the promotional code.
   */
  private String prepareResponseAfterRedeeming(
      List<String> activated, Map<String, String> others, String trouble) {
    String response = null;
    if (!activated.isEmpty()) {
      response = "Промокод был активирован на следующие IGG ID:\n"
          + String.join("\n", activated);
    }

    if (!others.isEmpty() && response != null) {
      response += "\nПромокод не был применен к следующим аккаунтам по причинам:\n"
          + others.entrySet()
          .stream()
          .map(e -> e.getKey() + ": " + e.getValue())
          .collect(Collectors.joining("\n"));
    } else if (!others.isEmpty()) {
      response = "Промокод не был применен к следующим аккаунтам по причинам:\n"
          + others.entrySet()
          .stream()
          .map(e -> e.getKey() + ": " + e.getValue())
          .collect(Collectors.joining("\n"));
    }

    if (activated.isEmpty() && others.isEmpty()) {
      response = "Применение промокода завершено досрочно по причине:\n" + trouble;
    }
    return response;
  }
}
