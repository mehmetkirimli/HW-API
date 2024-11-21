package org.api_resolver.bean.login;

import lombok.RequiredArgsConstructor;
import org.api_resolver.dto.ConnectionPayload;
import org.api_resolver.dto.ResponsePayload;
import org.api_resolver.dto.TokenDTO;
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
    private final TokenDTO tokenDTO;
    private final ResponseResolver responseResolver;
    private final JWTResolver jwtResolver;
    public String login()
    {
        super.disableSSLVerification();
        String response = sendPostRequest(getUrl1(),getEmail(),getPassword());
        tokenDTO.setToken(responseResolver.parseToken(response));

        return "-- Token : " + tokenDTO.getToken() + "\n -- MerchantId : 53";
    }
    public String sendPostRequest(String urlString, String email, String password)
    {
        try {
            String jsonInputString = "{"
                    + " \"email\": \"" + email
                    + "\", \"password\": \"" + password
                    + "\" }";

            URL url = new URL(urlString);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true); // connection open

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
