package org.api_resolver.utils;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;

@Component
public class ResponseResolver {

    public String parseToken(String jsonResponse)
    {
        // JSON cevabını ayrıştır
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        String token = jsonObject.get("token").getAsString();
        String status = jsonObject.get("status").getAsString();

        if (!"APPROVED".equalsIgnoreCase(status))
        {
            throw new RuntimeException("Status is not approved. Status: " + status);
        }

        return token;
    }
}
