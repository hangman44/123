package com.hukapp.service.auth.common.exception;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.hukapp.service.auth.common.exception.custom.AuthException;
import com.hukapp.service.auth.common.exception.custom.ResourceNotFoundException;
import com.hukapp.service.auth.common.exception.custom.UnexpectedStatusException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.FieldError;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private final MessageSource messageSource;
    private final String errorKey;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
        this.errorKey = messageSource.getMessage("response.error.key", null, LocaleContextHolder.getLocale());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Locale locale = LocaleContextHolder.getLocale();
        Map<String, String> errors = new HashMap<>();
        errors.put(errorKey, messageSource.getMessage("error.generic", null, locale));
        log.error(ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ NoResourceFoundException.class, NoHandlerFoundException.class,
            HttpRequestMethodNotSupportedException.class })
    public ResponseEntity<Map<String, String>> handleNoResourceFoundException(Exception ex) {
        Locale locale = LocaleContextHolder.getLocale();
        Map<String, String> errors = new HashMap<>();
        errors.put(errorKey, messageSource.getMessage("error.invalid.address", null, locale));
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.warn("Field validation error occured");
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        Map<String, String> errors = new HashMap<>();
        String detailMessage = ex.getMostSpecificCause().getMessage();
        String detail = detailMessage.substring(detailMessage.indexOf("Detail:") + 8).trim().replace("\"", "===");
        detail = StringUtils.substringBetween(detail, "===", "===");
        errors.put(errorKey, messageSource.getMessage("error.duplicate.record", null, locale));
        errors.put("detail", detail);
        log.warn("Data integrity violation error occurred: {}", detail);
        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Map<String, String>> handleAuthException(AuthException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(errorKey, ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnexpectedStatusException.class)
    public ResponseEntity<Map<String, String>> handleUnexpectedStatusException(UnexpectedStatusException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(errorKey, ex.getMessage());
        log.error("Unexpected status exception occurred: {}", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(errorKey, "Invalid request body");
        log.warn("Http message not readable exception occurred: {}", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(
        MissingServletRequestParameterException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(errorKey, ex.getMessage());
        log.warn("MissingServletRequestParameterException occurred: {}", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(errorKey, ex.getMessage());
        log.warn("Resource not found exception occurred: {}", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

}