package com.borrowboss.apigateway.Service;

import org.springframework.stereotype.Service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;


import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY="404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    private Key getSigninKey() {
        byte[] keyBites= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBites);
    }
    public void validateToken(String token) {
        Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token);
    }
}

