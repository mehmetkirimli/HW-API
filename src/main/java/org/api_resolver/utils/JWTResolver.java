package org.api_resolver.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JWTResolver {

    public int extractMerchantId(String token) //TODO HS256'ya Göre Tekrar Düzenle
    {
        Claims claims = Jwts.parserBuilder()
                .build()
                .parseClaimsJws(token)
                .getBody();

        //TODO merchantId değerini al
        return claims.get("merchantId", Integer.class);
    }

    // NOTE jwt.io web sitesinden token'ı parse edince anlaşılıyor bu durum .
    // Ben kendim jwt çzöümleyince digital signing key hatası oluşuyor , imzalayıcınin anahtarı lazım bunun içim.
}
