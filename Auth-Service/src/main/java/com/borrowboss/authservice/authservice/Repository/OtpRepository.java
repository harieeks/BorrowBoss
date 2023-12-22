package com.borrowboss.authservice.authservice.Repository;

import com.borrowboss.authservice.authservice.Entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<Otp,Long> {

    Otp findByUserId(Long id);
    Otp findByOtp(String otp);
}
