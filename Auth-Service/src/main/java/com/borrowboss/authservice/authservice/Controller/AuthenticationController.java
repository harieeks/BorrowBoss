package com.borrowboss.authservice.authservice.Controller;

import com.borrowboss.authservice.authservice.Dto.AuthenticateRequest;
import com.borrowboss.authservice.authservice.Dto.AuthenticationResponse;
import com.borrowboss.authservice.authservice.Dto.RegisterRequest;
import com.borrowboss.authservice.authservice.Dto.RegistrationResponse;
import com.borrowboss.authservice.authservice.Entity.User;
import com.borrowboss.authservice.authservice.Service.AuthService;
import com.borrowboss.authservice.authservice.Service.OtpService;
import com.borrowboss.authservice.authservice.Service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthService authService;
    private final TokenService tokenService;
    private final OtpService otpService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(
            @RequestBody RegisterRequest request
    ){
        RegistrationResponse response=authService.registerUser(request);
        if(response.isSuccess()){
            User user=(User) response.getResult();
            return ResponseEntity.ok(user);
        }else {
            String error=(String) response.getResult();
            return ResponseEntity.badRequest().body(error);
        }
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

    @PostMapping("/verify/{id}")
    public String verifyUser(
            @PathVariable("id")Long id,
            @RequestBody Map<String,String> otpMap
    ){
        return otpService.verifyOtp(id,otpMap.get("otp"));
    }

    @GetMapping("/hai")
    public String hai(){
        return "hai from secured end point";
    }

}
