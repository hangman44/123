package com.hukapp.service.auth.modules.person.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hukapp.service.auth.modules.person.entity.Otp;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {

    Optional<Otp> findByEmailAndOtpValueAndIsUsedFalseAndExpirationTimeAfter(String email, String otpValue, LocalDateTime now);

    Optional<Otp> findByEmailAndOtpValueAndIsUsedTrueAndExpirationTimeAfter(String email, String otp, LocalDateTime now);

}
