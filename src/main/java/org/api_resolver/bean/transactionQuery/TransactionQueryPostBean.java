package org.api_resolver.bean.transactionQuery;

import lombok.RequiredArgsConstructor;
import org.api_resolver.dto.ConnectionPayload;
import org.api_resolver.dto.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.net.ssl.HttpsURLConnection;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionQueryPostBean extends ConnectionPayload
{
    private final TokenDTO tokenDTO;

    public String transactionQuery()
    {
        super.disableSSLVerification();
        sendPostRequest();
        return "BU ÇALIŞIRSA OLMUŞTUR :) ";
    }

    public void sendPostRequest()
    {
        try
        {
            // JSON body (örnek parametrelerle)
            String jsonInputString = "{"
                    + "\"fromDate\": \"2015-07-01\","
                    + "\"toDate\": \"2015-10-01\","
                    + "\"merchantId\": 3,"
                    + "\"acquirerId\": 2"
                    + "}";

            URL apiUrl = new URL(getUrl3());
            HttpsURLConnection conn = (HttpsURLConnection) apiUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer "+tokenDTO.getToken());
            conn.setDoOutput(true);

            // TLS Protokolünü Zorunlu Kılın
            conn.setSSLSocketFactory((javax.net.ssl.SSLSocketFactory) javax.net.ssl.SSLSocketFactory.getDefault());

            // JSON body'yi gönder
            try (OutputStream os = conn.getOutputStream())
            {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Yanıtı Okuma
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK)
            {
                // Yanıtı okuyun
                InputStream is = conn.getInputStream();
                String response = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                System.out.println("Başarılı Yanıt: " + response);
            }
            else
            {
                System.out.println("Hata Kodu: " + responseCode);
                InputStream errorStream = conn.getErrorStream();
                if (errorStream != null) {
                    String errorResponse = new String(errorStream.readAllBytes(), StandardCharsets.UTF_8);
                    System.out.println("Hata Yanıtı: " + errorResponse);
                }
            }
            conn.disconnect();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }







}
