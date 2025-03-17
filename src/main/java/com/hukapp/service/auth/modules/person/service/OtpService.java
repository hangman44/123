package com.hukapp.service.auth.modules.person.service;

import com.hukapp.service.auth.common.dto.response.BaseResponse;
import com.hukapp.service.auth.modules.person.dto.response.OtpValidationResponse;

public interface OtpService {
    BaseResponse generateAndSendOtp(String email);
    OtpValidationResponse validateOtp(String email, String otp);
}
