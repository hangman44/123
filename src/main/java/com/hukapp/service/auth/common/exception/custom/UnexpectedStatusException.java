package com.hukapp.service.auth.common.exception.custom;

public class UnexpectedStatusException extends RuntimeException {

    public UnexpectedStatusException(String message) {
        super(message);
    }

    public UnexpectedStatusException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
