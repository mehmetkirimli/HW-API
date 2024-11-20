package org.api_resolver.bean.login;

import org.api_resolver.dto.ConnectionPayload;
import org.api_resolver.dto.ResponsePayload;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

@Component
public class LoginPostBean extends ConnectionPayload
{
    public String login()
    {
        super.disableSSLVerification();
        return sendPostRequest(getUrl1(),getEmail(),getPassword());
    }
    public String sendPostRequest(String urlString, String email, String password) {
        try {
            // URL oluşturuluyor
            URL url = new URL(urlString);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            // JSON veri
            String jsonInputString = "{ \"email\": \"" + email + "\", \"password\": \"" + password + "\" }";

            // Bağlantıyı açıyoruz ve veri gönderiyoruz
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream())
            {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Yanıtı alıyoruz
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            StringBuilder response = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8")))
            {
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                {
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
