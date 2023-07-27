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

@Service
@RequiredArgsConstructor
public class PromoCodeRedeemService {
  private final ObjectMapper objectMapper;
  private final PromoCodeService promoCodeService;
  private static final String URL = "https://dut.igg.com/event/code?lang=";

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
        if (apiResponse.msg().endsWith("[-54]")
            || apiResponse.msg().endsWith("[-57]")
            || apiResponse.msg().endsWith("[-51]")) {
          activatedIds.clear();
          othersIds.clear();
          promoCodeService.savePromoCode(promoCode);
          if (apiResponse.msg().endsWith("[-51]")) {
            trouble = "Такой промокод не существует";
          } else if (apiResponse.msg().endsWith("[-57]")) {
            trouble = "Время действия промокода истекло";
          } else {
            trouble = "Промокод уже был применен на аккаунты из списка";
          }
          break;
        }

        if (apiResponse.code() != 0) {
          othersIds.put(String.valueOf(ia.getIggId()), apiResponse.msg());
        } else {
          activatedIds.add(String.valueOf(ia.getIggId()));
        }

        // Не забываем освободить ресурсы
        EntityUtils.consume(entity);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    return prepareResponseAfterRedeeming(activatedIds, othersIds, trouble);
  }

  private String prepareResponseAfterRedeeming(
      List<String> activated, Map<String, String> others, String trouble) {
    String response = null;
    if (!activated.isEmpty()) {
      response = "Промокод был активирован на следующие IGG ID:\n"
          + String.join("\n", activated);
    }

    if (!others.isEmpty() && response != null) {
      response += " Промокод не был применен к следующим аккаунтам по причинам:\n"
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
