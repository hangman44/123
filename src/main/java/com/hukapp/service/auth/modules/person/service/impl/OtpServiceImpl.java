package com.hukapp.service.auth.modules.person.service.impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.hukapp.service.auth.common.dto.response.BaseResponse;
import com.hukapp.service.auth.common.exception.custom.AuthException;
import com.hukapp.service.auth.modules.person.dto.response.OtpValidationResponse;
import com.hukapp.service.auth.modules.person.entity.Otp;
import com.hukapp.service.auth.modules.person.entity.Person;
import com.hukapp.service.auth.modules.person.repository.OtpRepository;
import com.hukapp.service.auth.modules.person.service.OtpService;
import com.hukapp.service.auth.modules.person.service.PersonService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class OtpServiceImpl implements OtpService {

    private static final int OTP_LENGTH = 8;
    private static final int EXPIRATION_MINUTES = 3;

    private final OtpRepository otpRepository;
    private final PersonService personService;
    private final MessageSource messageSource;

    public OtpServiceImpl(OtpRepository otpRepository, PersonService personService, MessageSource messageSource) {
        this.otpRepository = otpRepository;
        this.personService = personService;
        this.messageSource = messageSource;
    }

    @Override
    public BaseResponse generateAndSendOtp(String email) {

        BaseResponse response = new BaseResponse();
        response.setResponseMessage(messageSource.getMessage("otp.sent", null, LocaleContextHolder.getLocale()));

        // TODO: Do not send OTP if the person is not found
        Optional<Person> person = personService.getPersonByEmail(email);
        if (person.isEmpty()) {
            log.info("Person with email {} not found", email);
            return response;
        }

        String otp = generateOtp();
        Otp otpEntity = new Otp();
        otpEntity.setEmail(email);
        otpEntity.setOtpValue(otp);
        otpEntity.setExpirationTime(LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES));
        otpEntity.setIsUsed(false);
        otpRepository.save(otpEntity);
        // Simulate sending email
        // TODO: Implement email sending logic
        log.info("Sending OTP {} to email {}", otp, email);
        
        return response;
    }

    @Override
    public OtpValidationResponse validateOtp(String email, String otp) {

        // Önce email ile eşleseşen bir kullanıcı var mı kontrol et
        Optional<Person> person = personService.getPersonByEmail(email);
        if (person.isEmpty()) {
            log.info("Person with email {} not found", email);
            throw new AuthException(messageSource.getMessage("otp.invalid.or.expired", null, LocaleContextHolder.getLocale()));
        }

        // TODO: Implement OTP invalidation logic when new OTP is generated
        // TODO: Implement try count and invalidation logic
        return otpRepository.findByEmailAndOtpValueAndIsUsedFalseAndExpirationTimeAfter(email, otp, LocalDateTime.now())
            .map(otpEntity -> {
                otpEntity.setIsUsed(true);
                otpRepository.save(otpEntity);
                OtpValidationResponse response = new OtpValidationResponse();
                response.setJwt("jwt");
                response.setResponseMessage(messageSource.getMessage("otp.valid", null, LocaleContextHolder.getLocale()));
                return response;
            }).orElseThrow(() -> new AuthException(messageSource.getMessage("otp.invalid.or.expired", null, LocaleContextHolder.getLocale())));
    }

    private String generateOtp() {
        Random random = new Random();
        int number = 10000000 + random.nextInt(90000000);
        return String.valueOf(number);
    }
}
