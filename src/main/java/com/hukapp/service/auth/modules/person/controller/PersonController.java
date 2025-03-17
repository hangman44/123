package com.hukapp.service.auth.modules.person.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hukapp.service.auth.modules.person.dto.request.ForgotPasswordRequest;
import com.hukapp.service.auth.modules.person.dto.request.PersonCreateRequest;
import com.hukapp.service.auth.modules.person.dto.request.PersonLoginRequest;
import com.hukapp.service.auth.modules.person.dto.request.UpdatePasswordRequest;
import com.hukapp.service.auth.modules.person.dto.request.ValidateOtpRequest;
import com.hukapp.service.auth.modules.person.dto.response.OtpValidationResponse;
import com.hukapp.service.auth.modules.person.dto.response.PersonCreateResponse;
import com.hukapp.service.auth.modules.person.dto.response.PersonLoginResponse;
import com.hukapp.service.auth.modules.person.dto.response.UpdatePasswordResponse;
import com.hukapp.service.auth.modules.person.entity.Person;
import com.hukapp.service.auth.modules.person.service.JwtService;
import com.hukapp.service.auth.modules.person.service.OtpService;
import com.hukapp.service.auth.modules.person.service.PersonService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import lombok.RequiredArgsConstructor;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

/**
 * The PersonController class handles HTTP requests for the /api/persons
 * endpoint.
 * It uses the PersonService to retrieve data about persons.
 */
@RestController
@RequestMapping("/auth/user")
@RequiredArgsConstructor // Generates a constructor with required arguments (final fields).
public class PersonController {

    private final PersonService personService;
    private final MessageSource messageSource;
    private final OtpService otpService; // added OTP service
    private final JwtService tokenService;

    @Value("${app.environment}")
    private String environment;

    @Operation(summary = "Get all persons. Development purpose only. Shouldn't exist on production environment", description = "Returns a list of all persons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "204", description = "No content found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Beklenmeyen bir hata oluştu, lütfen daha sonra tekrar deneyin.", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        if (!environment.equals("dev")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<Person> persons = personService.getAllPersons();
        return persons.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(persons);
    }

    @Operation(summary = "Get a person by TCKN. Development purpose only. Shouldn't exist on production environment", description = "Returns a person based on TCKN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved person"),
            @ApiResponse(responseCode = "404", description = "Person not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Beklenmeyen bir hata oluştu, lütfen daha sonra tekrar deneyin.", content = @Content)
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Person> getPersonByIdentityNumber(@PathVariable Long id) {
        if (!environment.equals("dev")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return personService.getPersonById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    ///////////////////////////////////////////////////////////////////////////////
    /**
     * Example endpoint, if we want to support versioning.
     */
    // @GetMapping(produces = "application/vnd.hukapp.v2+json")
    // public ResponseEntity<List<Person>> exampleEndpointWithVersioning() {
    // return ResponseEntity.noContent().build();
    // }
    ///////////////////////////////////////////////////////////////////////////////

    @Operation(summary = "Create a new person", description = "Creates a new person with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created person"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "409", description = "Tekrar eden kayıt.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Beklenmeyen bir hata oluştu, lütfen daha sonra tekrar deneyin.", content = @Content)
    })
    @PostMapping("create")
    public ResponseEntity<PersonCreateResponse> createPerson(
            @RequestBody @Valid PersonCreateRequest personCreateRequest) {

        PersonCreateResponse response = personService.savePerson(personCreateRequest);
        response.setResponseMessage(
                messageSource.getMessage("person.created.success", null, LocaleContextHolder.getLocale()));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Login a person", description = "Logs in a person with the provided credentials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully logged in"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content),
            @ApiResponse(responseCode = "500", description = "Beklenmeyen bir hata oluştu, lütfen daha sonra tekrar deneyin.", content = @Content)
    })
    @PostMapping("login")
    public ResponseEntity<PersonLoginResponse> login(@RequestBody @Valid PersonLoginRequest personLoginRequest) {

        PersonLoginResponse response = personService.login(personLoginRequest);

        // Buraya kadar exception fırlatmadıysa, login işlemi başarılıdır. jwt oluşturulur.
        String token = tokenService.generateToken(personLoginRequest.getEmail());
        response.setJwt(token);
        response.setResponseMessage(
                messageSource.getMessage("person.login.success", null, LocaleContextHolder.getLocale()));
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Forgot password. Generates an OTP and sends it to the provided email", description = "If the user exists, sends a random 8-digit OTP to email with 3 minutes expiration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OTP sent", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping("/forgot-password/send-otp")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {
        personService.getPersonByEmail(request.getEmail());
        otpService.generateAndSendOtp(request.getEmail());
        Map<String, String> response = new HashMap<>();
        response.put("message", messageSource.getMessage("otp.sent", null, LocaleContextHolder.getLocale()));
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Validate OTP", description = "Validates the provided OTP for the given email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OTP is valid", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid OTP", content = @Content)
    })
    @PostMapping("forgot-password/validate-otp")
    public ResponseEntity<OtpValidationResponse> validateOtp(@RequestBody @Valid ValidateOtpRequest request) {

        return ResponseEntity.ok(otpService.validateOtp(request.getOtpEmail(), request.getOtpValue()));
    }

    @Operation(summary = "Update password", description = "Updates the password for the given email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password updated successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping("forgot-password/update-password")
    public ResponseEntity<UpdatePasswordResponse> updatePassword(@RequestBody @Valid UpdatePasswordRequest request) {

        personService.updatePassword(request.getValidatedOtp().getOtpEmail(),
                request.getValidatedOtp().getOtpValue(), request.getPassword());
        return ResponseEntity.ok(new UpdatePasswordResponse("Password updated successfully"));
    }
}
