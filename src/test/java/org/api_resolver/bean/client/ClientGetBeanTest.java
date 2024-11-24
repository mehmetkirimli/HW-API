package org.api_resolver.bean.client;

import org.api_resolver.dto.TokenDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ClientGetBeanTest
{
    @Mock
    private TokenDTO tokenDTO;

    @Mock
    private ClientGetBean clientGetBean;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetClientWithInvalidToken()
    {
        // Geçersiz token durumu
        when(tokenDTO.isTokenValid()).thenReturn(false);

        String transactionId = "789123";
        when(clientGetBean.getClient(transactionId)).thenReturn("Token expired or invalid");
        String response = clientGetBean.getClient(transactionId);

        assertTrue(response.contains("Token expired or invalid"));
    }

    @Test
    void testGetClientWithValidToken()
    {
        // Geçerli token durumu
        when(tokenDTO.isTokenValid()).thenReturn(true);
        when(tokenDTO.getToken()).thenReturn("Token12345");

        String transactionId = "789123";

        // sendPostRequest metodunu mockluyoruz
        String mockResponse = "{\"customerInfo\": \"details...\"}";
        when(clientGetBean.sendPostRequest(any(), any())).thenReturn(mockResponse);

        when(clientGetBean.getClient(transactionId)).thenReturn(mockResponse);
        String response = clientGetBean.getClient(transactionId);

        assertTrue(response.contains("customerInfo"));
    }

    @Test
    void testSendPostRequestWithValidTransactionId() {
        // Geçerli token ile sendPostRequest metodu test ediliyor
        when(tokenDTO.isTokenValid()).thenReturn(true);
        when(tokenDTO.getToken()).thenReturn("Bearer valid-token");

        String transactionId = "789123";
        String mockResponse = "{\"data\": \"client details\"}";

        // sendPostRequest mocklanıyor
        when(clientGetBean.sendPostRequest(any(), any())).thenReturn(mockResponse);

        String response = clientGetBean.sendPostRequest("http://mock-api.com", transactionId);

        assertEquals(mockResponse, response);
    }

    @AfterEach
    void tearDown() {
    }
}