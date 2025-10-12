package com.example.blog_platform.security.JWT;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${spring.app.jwtSecret}")
    String secretKey;
    @Value("${spring.app.jwtExpirationMs}")
    long expirationMs;

    public String generateTokenFromUserName(String userName){
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expirationMs))
                .signWith(Key())
                .compact();
    }

    private Key Key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
    public String getUsernameFromToken(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) Key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String getJwtFromHeader(HttpServletRequest http){
        String bearerToken = http.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
    public boolean validateJwtToken(String authToken){
        try {
            System.out.println("validate");
            Jwts.parser().verifyWith((SecretKey) Key()).build().parseSignedClaims(authToken);
            return true;
        }
        catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }


}
