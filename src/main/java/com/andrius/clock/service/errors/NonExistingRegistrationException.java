package com.andrius.clock.service.errors;

public class NonExistingRegistrationException extends RegistrationException {
    public NonExistingRegistrationException(String endpoint) {
        super("Trying to deregister non existing endpoint " + endpoint);
    }
}
