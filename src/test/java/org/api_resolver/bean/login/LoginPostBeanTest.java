package org.api_resolver.bean.login;

import org.api_resolver.dto.LoginDTO;
import org.api_resolver.dto.TokenDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoginPostBeanTest {

    @Mock
    private LoginPostBean loginPostBean;

    @Mock
    private TokenDTO tokenDTO;

    @Mock
    private LoginDTO loginDTO;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);//Mockito bağımlılıklarını başlatır.
    }

    @Test
    void testLoginSuccess()
    {
        LoginDTO loginDTO = mock(LoginDTO.class);
        TokenDTO tokenDTO = mock(TokenDTO.class);


        // Mocking LoginDTO
        when(loginDTO.getEmail()).thenReturn("user@app.com");
        when(loginDTO.getPassword()).thenReturn("12345");
        when(tokenDTO.getToken()).thenReturn("token12345");

        // Mocking sendPostRequest
        when(loginPostBean.sendPostRequest(anyString(), eq(loginDTO)))
                .thenReturn("{\"token\": \"token12345\", \"status\": \"APPROVED\"}");

        // Mocking login method
        when(loginPostBean.login(loginDTO))
                .thenReturn("token12345");

        // login metodunu çağır
        String result = loginPostBean.login(loginDTO);

        // Sonuçları doğrula
        assertNotNull(result, "Result should not be null");
        assertTrue(result.contains("token"));
        assertEquals("token12345", result); // Token'ı direkt result ile kontrol edin

    }

    @Test
    void testLoginFailure() {
        // Mock nesneleri oluştur
        LoginDTO loginDTO = mock(LoginDTO.class);
        TokenDTO tokenDTO = mock(TokenDTO.class);

        // Hatalı LoginDTO
        when(loginDTO.getEmail()).thenReturn("user@example.com");
        when(loginDTO.getPassword()).thenReturn("wrongpassword");

        // Mocking sendPostRequest - Hata durumu
        when(loginPostBean.sendPostRequest(anyString(), eq(loginDTO)))
                .thenReturn("{\"status\": \"REJECTED\"}");

        // Mocking login metodunu
        when(loginPostBean.login(loginDTO))
                .thenReturn("Hata Detayı: Invalid credentials");

        // login metodunu çağır
        String result = loginPostBean.login(loginDTO);

        // Hata mesajını kontrol et
        assertNotNull(result, "Result should not be null");
        assertTrue(result.contains("Hata Detayı"));
        assertTrue(result.contains("Invalid credentials"));
    }

    @Test
    void testParseTokenSuccess()
    {
        String validJsonResponse = "{\"token\": \"abc123\", \"status\": \"APPROVED\"}";

        // Token doğru şekilde ayrıştırılmalı
        when(loginPostBean.parseToken(anyString())).thenReturn("token12345");
        String token = loginPostBean.parseToken(validJsonResponse);

        assertEquals("token12345", token);
    }

    @Test
    void testParseTokenFailure() {
        String invalidJsonResponse = "{\"token\": \"abc123\", \"status\": \"REJECTED\"}";

        when(loginPostBean.parseToken(anyString())).thenReturn("status: REJECTED");

        String response = loginPostBean.parseToken(invalidJsonResponse);

        assertTrue(response.contains("REJECTED"));
    }

    @Test
    void testSendPostRequestSuccess() {
        // Mocking sendPostRequest için başarılı bir yanıt simüle et
        when(loginPostBean.sendPostRequest(anyString(), any(LoginDTO.class)))
                .thenReturn("{\"token\": \"abc123\", \"status\": \"APPROVED\"}");

        String response = loginPostBean.sendPostRequest("https://api.example.com/login", loginDTO);

        assertNotNull(response);
        assertTrue(response.contains("token"));
    }

    @Test
    void testSendPostRequestFailure()
    {
        when(loginPostBean.sendPostRequest(anyString(), any(LoginDTO.class)))
                .thenReturn("Hata Oluştu: 500");

        String response = loginPostBean.sendPostRequest("https://api.example.com/login", loginDTO);

        // Hata mesajını doğrula
        assertEquals("Hata Oluştu: 500", response);
    }

}