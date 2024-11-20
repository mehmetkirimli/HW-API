package org.api_resolver.bean.client;

import org.api_resolver.dto.ConnectionPayload;
import org.springframework.stereotype.Component;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

@Component
public class ClientGetBean extends ConnectionPayload
{
    public String getClient()
    {
        super.disableSSLVerification();
        return null;
    }

    public String sendPostRequest(String urlString, String email, String password, String transactionId, String authorizationToken) {
        try {
            // URL oluşturuluyor
            URL url = new URL(urlString);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            // Authorization bilgisi ekleniyor
            connection.setRequestProperty("Authorization", "Bearer " + authorizationToken);

            // JSON veri
            String jsonInputString = "{ \"email\": \"" + email + "\", \"password\": \"" + password + "\", \"transactionId\": \"" + transactionId + "\" }";

            // Bağlantıyı açıyoruz ve veri gönderiyoruz
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Yanıtı alıyoruz
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            StringBuilder response = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                System.out.println("Response: " + response.toString());
            }
            return response.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error - Not Found API ....";
    }

}
