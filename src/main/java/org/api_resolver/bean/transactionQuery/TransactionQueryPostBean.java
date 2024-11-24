package org.api_resolver.bean.transactionQuery;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.api_resolver.dto.ConnectionPayload;
import org.api_resolver.dto.TokenDTO;
import org.api_resolver.dto.TransactionQueryDTO;
import org.api_resolver.mapper.TransactionQueryDTOMapper;
import org.api_resolver.utils.FieldUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionQueryPostBean extends ConnectionPayload
{
    private final TokenDTO tokenDTO;
    private final TransactionQueryDTOMapper mapper;

    public String transactionQuery(TransactionQueryDTO json)
    {
        String response = sendPostRequest(getUrl3(), json);
        if (response!= null)
        {
            return response;
        }
        return "Application is not connect to API !";
    }
    public String sendPostRequest(String urlString,TransactionQueryDTO json) {
        try {
            if (!tokenDTO.isTokenValid())
            {
                throw new RuntimeException("Token expired or invalid.");
            }

            TransactionQueryDTO mappedData = mapTransactionQuery(json);

            ObjectMapper objectMapper = new ObjectMapper();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            objectMapper.setDateFormat(sdf);
            String jsonInputString = objectMapper.writeValueAsString(mappedData);

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
                    System.out.println("Error Response : " + errorResponse.toString());
                    return "Error Detail : " + errorResponse.toString();
                }
                return "Error : " + responseCode;
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

    private TransactionQueryDTO mapTransactionQuery(TransactionQueryDTO json)
    {
        int paramCount = FieldUtils.countNonNullFields(json);

        switch ((int) paramCount) {
            case 0:
                return mapper.mapNoData(json);
            case 2:
                return mapper.mapTwoData(json);
            case 3:
                return mapper.mapThreeData(json);
            case 4:
                return mapper.mapFourData(json);
            case 10:
                return mapper.mapTenData(json);
            default:
                throw new IllegalArgumentException("Unsupported request data format");
        }
    }
}



