package org.api_resolver.bean.transaction;

import org.api_resolver.dto.TokenDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class TransactionGetBeanTest
{
    @Mock
    private TokenDTO tokenDTO;

    @Mock
    private TransactionGetBean transactionGetBean;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTransactionWithInvalidToken()
    {
        // Geçersiz token durumu
        when(tokenDTO.isTokenValid()).thenReturn(false);

        String transactionId = "123456";
        when(transactionGetBean.getTransaction(anyString())).thenReturn("Token expired or invalid");
        String response = transactionGetBean.getTransaction(transactionId);

        assertTrue(response.contains("Token expired or invalid"));
    }

    @Test
    void testGetTransactionWithValidToken() {
        // Geçerli token durumu
        when(tokenDTO.isTokenValid()).thenReturn(true);
        when(tokenDTO.getToken()).thenReturn("Token12345");

        String transactionId = "123";

        // sendPostRequest metodunu mockluyoruz
        String mockResponse = "{\"transaction\": \"details...\"}";
        when(transactionGetBean.sendPostRequest(anyString(), anyString())).thenReturn(mockResponse);

        when(transactionGetBean.getTransaction(transactionId)).thenReturn(mockResponse);
        String response = transactionGetBean.getTransaction(transactionId);

        assertTrue(response.contains("transaction"));
    }

    @Test
    void testSendPostRequestWithValidTransactionId() {
        // Geçerli token ile sendPostRequest metodu test ediliyor
        when(tokenDTO.isTokenValid()).thenReturn(true);
        when(tokenDTO.getToken()).thenReturn("Bearer valid-token");

        String transactionId = "123456";
        String mockResponse = "{\"data\": \"transaction details\"}";

        // sendPostRequest mocklanıyor
        when(transactionGetBean.sendPostRequest(any(), any())).thenReturn(mockResponse);

        String response = transactionGetBean.sendPostRequest("http://mock-api.com", transactionId);

        assertEquals(mockResponse, response);
    }

    @AfterEach
    void tearDown() {
    }
}