package com.borrowboss.authservice.authservice.Controller;

import com.borrowboss.authservice.authservice.Dto.AuthenticateRequest;
import com.borrowboss.authservice.authservice.Dto.AuthenticationResponse;
import com.borrowboss.authservice.authservice.Dto.RegisterRequest;
import com.borrowboss.authservice.authservice.Service.AuthService;
import com.borrowboss.authservice.authservice.Service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthService authService;
    private final TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
         return  ResponseEntity.ok(authService.registerUser(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticateRequest request
    ){
        return ResponseEntity.ok(authService.authenticate(request));
    }
    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        tokenService.refreshToken(request,response);
    }

    @GetMapping("/hai")
    public String hai(){
        return "hai from secured end point";
    }

}
