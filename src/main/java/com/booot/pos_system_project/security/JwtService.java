package com.booot.pos_system_project.security;

import com.booot.pos_system_project.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey signingKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
    public String generateToken(UserDetails user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet()));

        return Jwts.builder()
                .claims(claims).signWith(signingKey())
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .compact();
    }

    public String generateToken(User user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRoles().stream()
                .map(role -> role.getName().name())
        .collect(Collectors.toSet()));

        return Jwts.builder()
                .claims(claims).subject(user.getName())
                .issuedAt(new Date()).signWith(signingKey())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .compact();
    }
    public String exactUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }
    public Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }
    public boolean isTokenValid(String token){
        return extractExpiration(token).before(new Date());
    }
    public boolean IsTokenValid(String token, UserDetails userDetails){
        String user =  exactUsername(token);
        return user.equals(userDetails.getUsername()) && isTokenValid(token);
    }
    public <T> T extractClaims(String token, Function<Claims,T> claimResolver){
        Claims claims = Jwts.parser()
                .verifyWith(signingKey())
                .build().parseSignedClaims(token)
                .getPayload();

        return claimResolver.apply(claims);
    }

}
