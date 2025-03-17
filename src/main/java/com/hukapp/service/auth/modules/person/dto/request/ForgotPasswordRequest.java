package com.hukapp.service.auth.modules.person.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordRequest {

    @NotBlank(message = "Email boş olamaz")
    @Email(message = "Geçersiz email")
    private String email;

}
