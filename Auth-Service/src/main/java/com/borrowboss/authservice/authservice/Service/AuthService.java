package com.borrowboss.authservice.authservice.Service;

import com.borrowboss.authservice.authservice.Config.JwtService;
import com.borrowboss.authservice.authservice.Dto.AuthenticateRequest;
import com.borrowboss.authservice.authservice.Dto.AuthenticationResponse;
import com.borrowboss.authservice.authservice.Dto.RegisterRequest;
import com.borrowboss.authservice.authservice.Dto.RegistrationResponse;
import com.borrowboss.authservice.authservice.Entity.Role;
import com.borrowboss.authservice.authservice.Entity.Token;
import com.borrowboss.authservice.authservice.Entity.TokenType;
import com.borrowboss.authservice.authservice.Entity.User;
import com.borrowboss.authservice.authservice.Repository.TokenRepository;
import com.borrowboss.authservice.authservice.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final EmailService emailService;

    Logger logger= LoggerFactory.getLogger(AuthService.class);

    public RegistrationResponse registerUser(RegisterRequest request){

        try {
            var existUser=userRepository.findByEmail(request.getEmail());
            if(existUser.isPresent())return new RegistrationResponse("Email already used",false);
            User user=modelMapper.map(request, User.class);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setEnabled(false);
            user.setRole(Role.USER);
            var savedUser=userRepository.save(user);
            String status=emailService.generateEmail(savedUser);
            return new RegistrationResponse(savedUser,true);
        }catch (Exception e){
            return new RegistrationResponse("Registration failed please try again later",false);
        }
    }

    public AuthenticationResponse authenticate(AuthenticateRequest authenticateRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticateRequest.getEmail(),
                        authenticateRequest.getPassword()
                )
        );
        var user= userRepository.findByEmail(authenticateRequest.getEmail())
                .orElseThrow();
        var jwtToken=jwtService.generateToken(user);
        var refreshToken=jwtService.generateRefreshToken(user);
        tokenService.revokeToken(user);
        tokenService.saveUserToken(user,jwtToken);
        return AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }



}
