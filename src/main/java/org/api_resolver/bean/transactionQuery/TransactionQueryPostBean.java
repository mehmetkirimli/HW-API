package org.api_resolver.bean.transactionQuery;

import lombok.RequiredArgsConstructor;
import org.api_resolver.dto.ConnectionPayload;
import org.api_resolver.dto.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
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
        String response = sendPostRequest(getUrl3());
        return "BU ÇALIŞIRSA OLMUŞTUR :) ";
    }

    public String sendPostRequest(String urlString)
    {
        try {
            String jsonInputString = "{"
                    //+ " \"email\": \"" + email
                    //+ "\", \"password\": \"" + password
                    + "}";

            URL url = new URL(urlString);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + tokenDTO.getToken());
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
