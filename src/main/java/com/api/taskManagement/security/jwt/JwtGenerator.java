package com.api.taskManagement.security.jwt;

import com.api.taskManagement.security.user.SecureUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtGenerator {
    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generateToken(Authentication authentication, Long expiration) {
        SecureUser authenticatedUser = (SecureUser) authentication.getPrincipal();
        String username = authenticatedUser.getUsername();
        return generateToken(username, expiration);
    }

    public String generateToken(String username, Long expiration) {
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + expiration);

        return Jwts.builder()
                .setSubject(username)
                .setIssuer("Task Management API")
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getSignature();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthenticationCredentialsNotFoundException("Invalid token");
        }
    }
}
