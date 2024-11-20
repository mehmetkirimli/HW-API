package org.api_resolver.dto;


import org.springframework.beans.factory.annotation.Value;

public class ConnectionPayload
{
    @Value("${email}")
    private String email;

    @Value("${password}")
    private String password;

    @Value("${url1-test}")
    private String url1;

    @Value("${url2-test}")
    private String url2;

    @Value("${url3-test}")
    private String url3;

    @Value("${url4-test}")
    private String url4;

    @Value("${url5-test}")
    private String url5;


}
