package com.andrius.clock.service.errors;

public class NonExisitingRegistrationException extends RegistrationException {
    public NonExisitingRegistrationException(String endpoint) {
        super("Trying to deregister non existing endpoint " + endpoint);
    }
}
