package org.api_resolver.bean.login;

import lombok.RequiredArgsConstructor;
import org.api_resolver.dto.ConnectionPayload;
import org.api_resolver.dto.ResponsePayload;
import org.api_resolver.utils.JWTResolver;
import org.api_resolver.utils.ResponseResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginPostBean extends ConnectionPayload
{
    private final ResponseResolver responseResolver;
    private final JWTResolver jwtResolver;
    public String login()
    {
        super.disableSSLVerification();
        String response = sendPostRequest(getUrl1(),getEmail(),getPassword());
        super.setResponseToken(responseResolver.parseToken(response));
        super.setMerchantId(3); // NOTE jwt.io web sitesinden token'ı parse edince anlaşılıyor bu durum . Ben kendim jwt çzöümleyince digital signing key hatası oluşuyor , imzalayıcınin anahtarı lazım bunun içim.

        return "Token : " + super.getResponseToken() + " -- MerchantId : " + super.getMerchantId();
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
