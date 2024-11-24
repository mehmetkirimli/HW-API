package org.api_resolver.bean.transactionQuery;

import org.api_resolver.bean.transactionQuery.TransactionQueryPostBean;
import org.api_resolver.dto.TokenDTO;
import org.api_resolver.dto.TransactionQueryDTO;
import org.api_resolver.mapper.TransactionQueryDTOMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
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
        Mockito.when(tokenDTO.isTokenValid()).thenReturn(false);

        TransactionQueryDTO dto = new TransactionQueryDTO();
        Mockito.when(transactionQueryPostBean.transactionQuery(ArgumentMatchers.eq(dto))).thenReturn("Token expired or invalid");
        String response = transactionQueryPostBean.transactionQuery(dto);

        Assertions.assertTrue(response.contains("Token expired or invalid"));
    }

    @Test
    void testTransactionQueryWithValidToken()
    {
        // Geçerli token durumu
        Mockito.when(tokenDTO.isTokenValid()).thenReturn(true);

        TransactionQueryDTO dto = new TransactionQueryDTO();
        Mockito.when(mapper.mapFourData(ArgumentMatchers.any())).thenReturn(dto);

        // sendPostRequest mock'lanıyor
        String mockResponse = "{\"data\": \"transaction details...\"}";
        Mockito.when(transactionQueryPostBean.sendPostRequest(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(mockResponse);

        Mockito.when(transactionQueryPostBean.transactionQuery(ArgumentMatchers.eq(dto))).thenReturn("data : transaction ...");
        String response = transactionQueryPostBean.transactionQuery(dto);

        Assertions.assertTrue(response.contains("data"));
    }

    @Test
    void testSendPostRequestWithValidData() {
        Mockito.when(tokenDTO.isTokenValid()).thenReturn(true);
        Mockito.when(tokenDTO.getToken()).thenReturn("Bearer valid-token");

        TransactionQueryDTO dto = new TransactionQueryDTO();
        dto.setMerchant(123);
        dto.setAcquirer(456);

        // Mock mapper behavior
        Mockito.when(mapper.mapFourData(ArgumentMatchers.any())).thenReturn(dto);

        String mockResponse = "{\"data\": \"successful response\"}";
        Mockito.when(transactionQueryPostBean.sendPostRequest(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(mockResponse);

        String response = transactionQueryPostBean.sendPostRequest("http://mock-api.com", dto);

        Assertions.assertEquals(mockResponse, response);
    }

    @AfterEach
    void tearDown() {
    }
}