package org.example.springmanager2.Config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.example.springmanager2.Entity.Enums.ROLES;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY ="SI-12-ACHRAF-08-SAADALI-2003-SI-12-ACHRAF-08-SAADALI-2003" ;

    private final long EXPIRATION = 1000 * 60 * 60; // 1 hour

    private Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    public String generateToken(String userEmail, ROLES userRole) {
        return Jwts.builder()
                .setSubject(userEmail)
                .claim("userRole" , userRole)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    public ROLES extractRole(String token)
    {
         String roleString = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userRole",String.class) ;
         return ROLES.valueOf(roleString);
    }

    public boolean isTokenValid(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.after(new Date());
    }
}
