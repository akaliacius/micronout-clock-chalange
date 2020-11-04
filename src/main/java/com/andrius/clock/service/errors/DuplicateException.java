package com.andrius.clock.service.errors;

public class DuplicateException extends RegistrationException {
    public DuplicateException(String endpoint) {
        super("Trying to subscribe with existing endpoint " + endpoint);
    }
}
