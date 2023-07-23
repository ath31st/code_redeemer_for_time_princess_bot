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
  private static final String URL = "https://dut.igg.com/event/code";

  public String redeemPromoCode(String promoCode, List<IggAccount> accounts) {
    List<Long> activatedIds = new ArrayList<>();
    Map<Long, String> othersIds = new HashMap<>();

    try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
      HttpPost httpPost = new HttpPost(URL);

      Collections.shuffle(accounts);
      for (IggAccount ia : accounts) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("iggid", String.valueOf(ia.getIggId())));
        params.add(new BasicNameValuePair("cdkey", promoCode));
        params.add(new BasicNameValuePair("username", ""));
        params.add(new BasicNameValuePair("sign", "0"));

        httpPost.setEntity(new UrlEncodedFormEntity(params));

        HttpResponse response = httpclient.execute(httpPost);

        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity);
        ApiResponse apiResponse = objectMapper.readValue(responseString, ApiResponse.class);

        if (apiResponse.code() != 0) {
          othersIds.put(ia.getIggId(), apiResponse.msg());
        } else {
          activatedIds.add(ia.getIggId());
        }

        // Не забываем освободить ресурсы
        EntityUtils.consume(entity);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    return " ";
  }


}
