package org.api_resolver.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.api_resolver.model.TransactionList;

import java.io.IOException;

public class JsonParser
{
    public TransactionList parseJsonString(String jsonString) throws IOException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, TransactionList.class);
    }
}
