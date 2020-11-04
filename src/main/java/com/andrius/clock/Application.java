package com.andrius.clock;

import com.andrius.clock.service.ClockJob;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.Micronaut;
import io.micronaut.runtime.server.event.ServerStartupEvent;

import javax.inject.Singleton;

@Singleton
public class Application implements ApplicationEventListener<ServerStartupEvent> {
    private final ClockJob clockJob;

    public Application(ClockJob clockJob) {
        this.clockJob = clockJob;
    }

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }

    @Override
    public void onApplicationEvent(ServerStartupEvent event) {
        clockJob.run();
    }
}
