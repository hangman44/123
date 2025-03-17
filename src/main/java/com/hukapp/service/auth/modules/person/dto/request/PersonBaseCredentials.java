package com.hukapp.service.auth.modules.person.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.stream.IntStream;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Person Data Transfer Object for Login")
public class PersonBaseCredentials {

    @NotBlank(message = "Parola gereklidir")
    @Schema(description = "Kişinin parolası", example = "********")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s])\\S{8,50}$", message = "Parola en az bir küçük harf, bir büyük harf, bir rakam, bir özel karakter içermeli ve 8 ile 50 karakter arasında olmalıdır")
    private String password;

    @jakarta.validation.constraints.AssertTrue(message = "Art arda rakam/harf kullanımı, parola yeterince güçlü değil")
    private boolean isPasswordStrength() {
        return !isWeakPassword(password);
    }

    private boolean isWeakPassword(String password) {
        return hasConsecutiveDigits(password) || hasConsecutiveChars(password) || hasConsecutiveAlphabeticOrder(password);
    }

    private boolean hasConsecutiveDigits(String password) {
        return IntStream.range(0, password.length() - 2)
                .anyMatch(i -> Character.isDigit(password.charAt(i)) &&
                               Character.isDigit(password.charAt(i + 1)) &&
                               Character.isDigit(password.charAt(i + 2)));
    }

    private boolean hasConsecutiveAlphabeticOrder(String password) {
        return IntStream.range(0, password.length() - 2)
                .anyMatch(i -> password.charAt(i + 1) == password.charAt(i) + 1 &&
                               password.charAt(i + 2) == password.charAt(i) + 2);
    }

    private boolean hasConsecutiveChars(String password) {
        return IntStream.range(0, password.length() - 2)
                .anyMatch(i -> password.charAt(i) == password.charAt(i + 1) &&
                               password.charAt(i) == password.charAt(i + 2));
    }
}
