package org.api_resolver.bean.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.api_resolver.dto.ConnectionPayload;
import org.api_resolver.dto.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginPostBean extends ConnectionPayload
{
    private final TokenDTO tokenDTO;
    public String login()
    {
        String response = sendPostRequest(getUrl1());
        tokenDTO.setToken(parseToken(response));
        return "-- Token : " + tokenDTO.getToken() + "\n -- MerchantId : 53";
    }
    public String parseToken(String jsonResponse)
    {
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        String token = jsonObject.get("token").getAsString();
        String status = jsonObject.get("status").getAsString();
        if (!"APPROVED".equalsIgnoreCase(status))
        {
            throw new RuntimeException("Status is not approved. Status: " + status);
        }
        return token;
    }
    public String sendPostRequest(String urlString)
    {
        try {
            Map<String, Object> body = new HashMap<>();
            body.put("email", getEmail());
            body.put("password", getPassword());

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonInputString = objectMapper.writeValueAsString(body);

            URL url = new URL(urlString);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setSSLSocketFactory((SSLSocketFactory) SSLSocketFactory.getDefault());
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream())
            {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Yanıt kodunu al
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode >= 200 && responseCode < 300)
            {
                StringBuilder response = new StringBuilder();
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8")))
                {
                    String inputLine;
                    while ((inputLine = in.readLine()) != null)
                    {
                        response.append(inputLine);
                    }
                }
                System.out.println("Response: " + response.toString());
                connection.disconnect();
                return response.toString();
            }
            else
            {
                InputStream errorStream = connection.getErrorStream();
                if (errorStream != null)
                {
                    StringBuilder errorResponse = new StringBuilder();
                    try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream, "utf-8")))
                    {
                        String line;
                        while ((line = errorReader.readLine()) != null)
                        {
                            errorResponse.append(line);
                        }
                    }
                    System.out.println("Hata Yanıtı: " + errorResponse.toString());
                    return "Hata Detayı: " + errorResponse.toString();
                }
                return "Hata Oluştu: " + responseCode;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return "Error - IOException";
        }
        catch (RuntimeException e)
        {
            System.err.println(e.getMessage());
            return "Error - " + e.getMessage();
        }
    }
}
