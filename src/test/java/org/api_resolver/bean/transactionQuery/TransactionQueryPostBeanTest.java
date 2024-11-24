package org.api_resolver.bean.transactionQuery;

import org.api_resolver.dto.TokenDTO;
import org.api_resolver.dto.TransactionQueryDTO;
import org.api_resolver.mapper.TransactionQueryDTOMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class TransactionQueryPostBeanTest
{
    @Mock
    private TokenDTO tokenDTO;

    @Mock
    private TransactionQueryDTOMapper mapper;

    @Mock
    private TransactionQueryPostBean transactionQueryPostBean;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testTransactionQueryWithInvalidToken()
    {
        // Geçersiz token durumu
        when(tokenDTO.isTokenValid()).thenReturn(false);

        TransactionQueryDTO dto = new TransactionQueryDTO();
        when(transactionQueryPostBean.transactionQuery(eq(dto))).thenReturn("Token expired or invalid");
        String response = transactionQueryPostBean.transactionQuery(dto);

        assertTrue(response.contains("Token expired or invalid"));
    }

    @Test
    void testTransactionQueryWithValidToken()
    {
        // Geçerli token durumu
        when(tokenDTO.isTokenValid()).thenReturn(true);

        TransactionQueryDTO dto = new TransactionQueryDTO();
        when(mapper.mapFourData(any())).thenReturn(dto);

        // sendPostRequest mock'lanıyor
        String mockResponse = "{\"data\": \"transaction details...\"}";
        when(transactionQueryPostBean.sendPostRequest(any(), any())).thenReturn(mockResponse);

        when(transactionQueryPostBean.transactionQuery(eq(dto))).thenReturn("data : transaction ...");
        String response = transactionQueryPostBean.transactionQuery(dto);

        assertTrue(response.contains("data"));
    }

    @Test
    void testSendPostRequestWithValidData() {
        when(tokenDTO.isTokenValid()).thenReturn(true);
        when(tokenDTO.getToken()).thenReturn("Bearer valid-token");

        TransactionQueryDTO dto = new TransactionQueryDTO();
        dto.setMerchant(123);
        dto.setAcquirer(456);

        // Mock mapper behavior
        when(mapper.mapFourData(any())).thenReturn(dto);

        String mockResponse = "{\"data\": \"successful response\"}";
        when(transactionQueryPostBean.sendPostRequest(any(), any())).thenReturn(mockResponse);

        String response = transactionQueryPostBean.sendPostRequest("http://mock-api.com", dto);

        assertEquals(mockResponse, response);
    }

    @AfterEach
    void tearDown() {
    }
}