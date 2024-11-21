package org.api_resolver.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class TokenDTO
{
    private String token;
    private Long tokenExpiration;

    public void setToken(String token)
    {
        this.token=token;
        this.tokenExpiration=System.currentTimeMillis() + (10*60*1000);
    }

    public Boolean isTokenValid()
    {
        return System.currentTimeMillis()<tokenExpiration;
    }
}
