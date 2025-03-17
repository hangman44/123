package com.hukapp.service.auth.modules.person.dto.request;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordRequest extends PersonBaseCredentials {

    private @Valid ValidateOtpRequest validatedOtp;
    
}
