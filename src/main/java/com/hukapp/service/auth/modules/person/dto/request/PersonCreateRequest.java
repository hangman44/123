package com.hukapp.service.auth.modules.person.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Person Data Transfer Object")
public class PersonCreateRequest extends PersonLoginRequest {

    @NotBlank(message = "İsim gereklidir")
    @Size(min = 2, max = 50)
    @Schema(description = "Person's name", example = "John")
    private String name;

    @NotBlank(message = "Soyisim gereklidir")
    @Size(min = 2, max = 50)
    @Schema(description = "Person's surname", example = "Doe")
    private String surname;

    @Schema(description = "Additional information about the person")
    @Size(max = 255, message = "Bilgi metni en fazla 255 karakter olmalıdır")
    private String personInfoText;

    @NotNull(message = "Kimlik numarası gereklidir")
    @Schema(description = "Person's identity number (TCKN)", example = "12345678901")
    private Long identityNumber;

    @NotNull(message = "Doğum tarihi gereklidir")
    @Schema(description = "Person's birth date", example = "1990-01-30")
    @Past(message = "Geçerli bir doğum tarihi giriniz")
    private LocalDate birthDate;

    @NotBlank(message = "Cep telefonu gereklidir")
    @Schema(description = "Person's mobile phone number", example = "5551234567")
    @Size(min = 10, max = 10, message = "Cep telefonu numarası tam olarak 10 haneli olmalıdır")
    private String mobilePhone;

}