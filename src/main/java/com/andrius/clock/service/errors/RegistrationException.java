package com.andrius.clock.service.errors;

public abstract class RegistrationException extends RuntimeException {
    public RegistrationException(String message) {
        super(message);
    }
}
