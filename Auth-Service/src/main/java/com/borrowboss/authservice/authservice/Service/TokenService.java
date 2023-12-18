package com.borrowboss.authservice.authservice.Service;

import com.borrowboss.authservice.authservice.Config.JwtService;
import com.borrowboss.authservice.authservice.Dto.AuthenticationResponse;
import com.borrowboss.authservice.authservice.Entity.Token;
import com.borrowboss.authservice.authservice.Entity.TokenType;
import com.borrowboss.authservice.authservice.Entity.User;
import com.borrowboss.authservice.authservice.Repository.TokenRepository;
import com.borrowboss.authservice.authservice.Repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Transactional
    public Optional<Token> getToken(String token){
        return tokenRepository.findByToken(token);
    }

    public void saveToken(Token token){
        tokenRepository.save(token);
    }

    public void revokeToken(User user){
        var userToken=tokenRepository.findAllValidTokensByUser(user.getId());
        userToken.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
    }
    public void saveUserToken(User user,String jwtToken){
        var token= Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader=request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }
        refreshToken=authHeader.substring(7);
        username=jwtService.extractUsername(refreshToken);
        if(username != null){
            var user=userRepository
                    .findByEmail(username)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken,user)){
                var accessToken=jwtService.generateToken(user);
                revokeToken(user);
                saveUserToken(user,accessToken);
                var authResponse= AuthenticationResponse
                        .builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(),authResponse);
            }
        }
    }
}
