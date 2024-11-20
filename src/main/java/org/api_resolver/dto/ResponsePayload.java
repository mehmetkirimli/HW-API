package org.api_resolver.dto;

public class ResponsePayload<T>
{
    private Integer statusCode;
    private T data;

    public ResponsePayload(){}

    public ResponsePayload(Integer code,T data) {
        this.statusCode=code;
        this.data = data;
    }
}
