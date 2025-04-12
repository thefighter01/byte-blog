package com.fighter.byteblog.services.impl;

import com.fighter.byteblog.services.AuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @Value("${jwt.secret}")
    private  String secret;

    @Value("${jwt.expiration.time.ms}")
    private  long expirationTimeMs;

    /*
    notes :
    1. create a jwt token
    2. return the token
    see what is the fucken happens after you pass the jwt as a bytes array
    SecretKey secretKey = hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    ask dep seek what is line doing
     */
    @Override
    public UserDetails authenticate(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        return userDetailsService.loadUserByUsername(email);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        Map<String , Object> claims = new HashMap();

        return Jwts .builder().setClaims(claims)
                .setSubject(userDetails.getUsername())
                .issuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + expirationTimeMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();


    }

    private SecretKey getSigningKey() {
        byte [] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        SecretKey secretKey = hmacShaKeyFor(keyBytes);
        return secretKey;
    }

    private String extractUsername(String token) {
        Claims claims = Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    @Override
    public UserDetails validateToken(String token) {
        String username = extractUsername(token);
        return userDetailsService.loadUserByUsername(username);
    }
}
