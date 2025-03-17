package com.hukapp.service.auth.modules.person.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateOtpRequest {

    @NotBlank(message = "OTP boş olamaz")
    @Size(min = 8, max = 8, message = "OTP 8 haneli olmalıdır")
    private String otpValue;

    @NotBlank(message = "Email boş olamaz")
    @Email(message = "Geçersiz email")
    private String otpEmail;
}
