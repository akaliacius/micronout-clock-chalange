package com.andrius.clock.controller;

import com.andrius.clock.service.ClockService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

@Controller("/clock")
public class ClockController {
    private final ClockService clockService;

    public ClockController(ClockService clockService) {
        this.clockService = clockService;
    }

    @Post
    @Produces(MediaType.APPLICATION_JSON)
    public RegisterRequest register(@Body RegisterRequest request) {
        clockService.register(request.getUrl());
        return request;
    }

    @Delete
    @Produces(MediaType.APPLICATION_JSON)
    public RegisterRequest deregister(@Body RegisterRequest request) {
        clockService.deregister(request.getUrl());
        return request;
    }

    @Put
    @Produces(MediaType.APPLICATION_JSON)
    public UpdateRequest update(@Body UpdateRequest request) {
        clockService.update(request.getSeconds());
        return request;
    }
}
