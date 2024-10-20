package com.webxemphim.demo.utils;

import java.util.Set;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;

import javax.crypto.SecretKey;

@Component
public class JwtUtilsHelper {

    @Value("${jwt.privateKey}")
    private String privateKey;

    private Set<String> blacklistedTokens = new HashSet<>();

    public String generateToken(String data) {

        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(privateKey));
        String jws = Jwts.builder().setSubject(data).signWith(key).compact();

        return jws;

    }

    public boolean verifyToken(String token) {
        try {

            if (blacklistedTokens.contains(token)) {
                return false; // Token đã bị đăng xuất
            }

            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(privateKey));
            Jwts.parserBuilder()                
                    .setSigningKey(key)             
                    .build()                      
                    .parseClaimsJws(token);

            return true;      
        } catch (Exception e) {
            return false;
        }
    }

    public void addTokenToBlacklist(String token) {
        blacklistedTokens.add(token);
    }
}
