package org.api_resolver.dto;

public class TokenManager
{
    private static TokenManager instance;
    private TokenDTO tokenDTO;

    private TokenManager()
    {
        tokenDTO = new TokenDTO();
    }
    public static TokenManager getInstance()
    {
        if(instance==null)
        {
            instance=new TokenManager();
        }
        return instance;
    }
    public TokenDTO getTokenDTO()
    {
        return tokenDTO;
    }
}
