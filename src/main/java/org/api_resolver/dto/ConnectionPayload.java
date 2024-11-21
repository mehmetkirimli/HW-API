package org.api_resolver.dto;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
@Getter
public class ConnectionPayload
{
    @Value("${email}")
    private String email;

    @Value("${password}")
    private String password;

    @Value("${url1-test}")
    private String url1;

    @Value("${url1-live}")
    private String liveUrl1;

    @Value("${url2-test}")
    private String url2;

    @Value("${url2-live}")
    private String liveUrl2;

    @Value("${url3-test}")
    private String url3;

    @Value("${url3-live}")
    private String liveUrl3;

    @Value("${url4-test}")
    private String url4;

    @Value("${url4-live}")
    private String liveUrl4;

    @Value("${url5-test}")
    private String url5;

    @Value("${url5-live}")
    private String liveUrl5;
}
