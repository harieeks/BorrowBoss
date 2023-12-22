package com.borrowboss.authservice.authservice.Service;

import com.borrowboss.authservice.authservice.Entity.Otp;
import com.borrowboss.authservice.authservice.Entity.User;
import com.borrowboss.authservice.authservice.Repository.OtpRepository;
import com.borrowboss.authservice.authservice.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService{

    private final OtpRepository otpRepository;
    private final UserRepository userRepository;
    private static final Logger logger= LoggerFactory.getLogger(OtpServiceImpl.class);

    public String generatePassword(User user){
        String otpNumber=generateOtp();
        saveOtp(otpNumber,user);
        return otpNumber;
    }

    private String generateOtp(){
        String otpNumber =  new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
        return otpNumber;
    }
    private void saveOtp(String otpNumber,User user){
        LocalDateTime issuedAt= LocalDateTime.now();
        var otp= Otp.builder()
                .otp(otpNumber)
                .issuedAt(issuedAt)
                .user(user)
                .build();
        otpRepository.save(otp);
    }

    @Override
    public String verifyOtp(Long id,String userOtp) {
        try {

            if(isOtpExpired(id)) return "Otp expired please try again later";
            Otp otp=otpRepository.findByUserId(id);


            System.out.println(userOtp);
            if (!userOtp.equals(otp.getOtp())) return "Incorrect otp";
            User user=userRepository.getUserById(id);
            user.setEnabled(true);
            userRepository.save(user);
            return "Otp verification success full";
        }catch (Exception e){
            System.out.println(e.getMessage());
            return "Something went wrong please try again later";
        }

    }

    private boolean isOtpExpired(Long id){
        Otp otp1=otpRepository.findByUserId(id);
        LocalDateTime currentTime=LocalDateTime.now();
        long minutes= ChronoUnit.MINUTES.between(otp1.getIssuedAt(), currentTime);
        return minutes >= 5;
    }

}
