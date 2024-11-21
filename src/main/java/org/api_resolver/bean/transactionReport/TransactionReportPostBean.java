package org.api_resolver.bean.transactionReport;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.api_resolver.dto.ConnectionPayload;
import org.api_resolver.dto.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionReportPostBean extends ConnectionPayload
{
    private final TokenDTO tokenDTO;

    public String transactionReport()
    {
        String response = sendPostRequest(getUrl2());
        if (response!= null)
        {
            return response;
        }
        return "Application is not connect to API !";
    }
    public String sendPostRequest(String urlString) {
        try {
            if (!tokenDTO.isTokenValid())
            {
                throw new RuntimeException("Token expired or invalid.");
            }

            Map<String, Object> body = new HashMap<>();
            body.put("fromDate", "2015-07-01");
            body.put("toDate", "2015-10-01");
            body.put("merchant","53");
            body.put("acquirer","1");

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonInputString = objectMapper.writeValueAsString(body);

            URL url = new URL(urlString);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", tokenDTO.getToken());
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
