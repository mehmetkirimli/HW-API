package org.api_resolver.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JWTResolver {

    public int extractMerchantId(String token)
    {
        // JWT'yi çözümle
        Claims claims = Jwts.parserBuilder()
                .build()
                .parseClaimsJws(token)
                .getBody();

        // merchantId değerini al
        return claims.get("merchantId", Integer.class);
    }
}
