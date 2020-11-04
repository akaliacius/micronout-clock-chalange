package com.andrius.clock.service.errors;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import javax.inject.Singleton;
import java.util.Map;

@Produces(MediaType.APPLICATION_JSON)
@Singleton
@Requires(classes = {NonExistingRegistrationException.class, ExceptionHandler.class})
public class NonExistingRegistrationHandler implements ExceptionHandler<NonExistingRegistrationException, HttpResponse> {
    @Override
    public HttpResponse handle(HttpRequest request, NonExistingRegistrationException exception) {
        return HttpResponse.status(HttpStatus.NOT_FOUND)
                .body(Map.of("reason", exception.getMessage()));
    }
}
