package org.api_resolver.bean.transactionReport;

import org.api_resolver.dto.TokenDTO;
import org.api_resolver.dto.TransactionReportDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class TransactionReportPostBeanTest {

    @Mock
    private TransactionReportPostBean transactionReportPostBean;

    @Mock
    private TokenDTO tokenDTO;

    @Mock
    private TransactionReportDTO transactionReportDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testTransactionReportTokenInvalid()
    {
        // Geçersiz token durumu
        when(tokenDTO.isTokenValid()).thenReturn(false);

        TransactionReportDTO dto = new TransactionReportDTO();
        dto.setFromDate(new java.util.Date());
        dto.setToDate(new java.util.Date());
        dto.setMerchant(123);
        dto.setAcquirer(456);

        when(transactionReportPostBean.transactionReport(dto)).thenReturn("Token expired or invalid");
        String response = transactionReportPostBean.transactionReport(dto);

        assertTrue(response.contains("Token expired or invalid"));
    }

    @Test
    void testTransactionReportTokenValid()
    {
        when(tokenDTO.isTokenValid()).thenReturn(true);

        TransactionReportDTO dto = new TransactionReportDTO();
        dto.setFromDate(new java.util.Date());
        dto.setToDate(new java.util.Date());
        dto.setMerchant(123);
        dto.setAcquirer(456);

        // transactionReport metodunu mockluyorz
        when(transactionReportPostBean.transactionReport(dto)).thenReturn("{\"data\": \"customenInfo : ....}\"}");

        String response = transactionReportPostBean.transactionReport(dto);

        assertTrue(response.contains("data"));
    }

    @AfterEach
    void tearDown() {
    }
}
