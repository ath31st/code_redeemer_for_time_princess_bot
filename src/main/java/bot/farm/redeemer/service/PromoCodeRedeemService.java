package bot.farm.redeemer.service;

import bot.farm.redeemer.entity.IggAccount;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
  private static final String URL = "https://dut.igg.com/event/code";

  public String redeemPromoCode(String promoCode, List<IggAccount> accounts) {
    try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
      HttpPost httpPost = new HttpPost(URL);

      for (IggAccount ia : accounts) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("iggid", String.valueOf(ia.getIggId())));
        params.add(new BasicNameValuePair("cdkey", promoCode));
        params.add(new BasicNameValuePair("username", ""));
        params.add(new BasicNameValuePair("sign", "0"));

        httpPost.setEntity(new UrlEncodedFormEntity(params));

        HttpResponse response = httpclient.execute(httpPost);

        // Получаем тело ответа
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity);

        // Выводим ответ на экран
        System.out.println("Response: " + responseString);

        // Не забываем освободить ресурсы
        EntityUtils.consume(entity);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }
}
