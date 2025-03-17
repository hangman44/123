package com.hukapp.service.auth.modules.person.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Person Data Transfer Object for Login")
public class PersonLoginRequest extends PersonBaseCredentials {

    @NotBlank(message = "Email gereklidir")
    @Email(message = "Geçersiz email")
    @Schema(description = "Person's email address", example = "john.doe@example.com")
    private String email;

}