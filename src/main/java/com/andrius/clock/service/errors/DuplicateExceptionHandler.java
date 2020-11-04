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
@Requires(classes = {DuplicateException.class, ExceptionHandler.class})
public class DuplicateExceptionHandler implements ExceptionHandler<DuplicateException, HttpResponse> {
    @Override
    public HttpResponse handle(HttpRequest request, DuplicateException exception) {
        return HttpResponse.status(HttpStatus.CONFLICT)
                .body(Map.of("reason", exception.getMessage()));
    }
}
