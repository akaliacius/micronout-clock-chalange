package com.andrius.clock.service;

import com.andrius.clock.service.errors.DuplicateException;
import com.andrius.clock.service.errors.NonExistingRegistrationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

@Singleton
public class ClockService {
    private static final Logger LOG = LoggerFactory.getLogger(ClockService.class);
    private final DataStorage dataStorage;
    private final ClockJob clockJob;

    public ClockService(DataStorage dataStorage, ClockJob clockJob) {
        this.dataStorage = dataStorage;
        this.clockJob = clockJob;
    }

    // that's very simple. we should validate endpoint
    public void register(String endpoint){
        if(!dataStorage.insertEndpoint(endpoint)){
            throw new DuplicateException(endpoint);
        }
    }

    public void deregister(String endpoint){
        if(!dataStorage.removeEndpoint(endpoint)){
            throw new NonExistingRegistrationException(endpoint);
        }
    }

    public void update(int seconds){
        clockJob.update(seconds);
    }
}
