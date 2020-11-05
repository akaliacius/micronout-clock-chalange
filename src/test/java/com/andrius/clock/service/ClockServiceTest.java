package com.andrius.clock.service;

import com.andrius.clock.service.errors.DuplicateException;
import com.andrius.clock.service.errors.NonExistingRegistrationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ClockServiceTest {
    public static final String URL = "https://www.example.com/boundary/aunt";

    private ClockService testable;
    private DataStorage dataStorage;
    private ClockJob clockJob;

    @BeforeEach void setup(){
        dataStorage = mock(DataStorage.class);
        clockJob = mock(ClockJob.class);
        testable = new ClockService(dataStorage, clockJob);
    }

    @Nested
    class RegisterTests {
        @Test void if_datastore_returns_true_register_should_void(){
            when(dataStorage.insertEndpoint(URL)).thenReturn(true);
            testable.register(URL);
        }

        @Test void if_datastore_returns_false_register_should_throw(){
            when(dataStorage.insertEndpoint(URL)).thenReturn(false);
            assertThrows(DuplicateException.class, () -> testable.register(URL));
        }

        @AfterEach void check(){
            verify(dataStorage, atLeastOnce()).insertEndpoint(URL);
        }
    }

    @Nested
    class DeRegisterTests {
        @Test void if_datastore_returns_true_deregister_should_void(){
            when(dataStorage.removeEndpoint(URL)).thenReturn(true);
            testable.deregister(URL);
            verify(dataStorage, atLeastOnce()).removeEndpoint(URL);
        }

        @Test void if_datastore_returns_false_deregister_should_throw(){
            when(dataStorage.removeEndpoint(URL)).thenReturn(false);
            assertThrows(NonExistingRegistrationException.class, () -> testable.deregister(URL));
            verify(dataStorage, atLeastOnce()).removeEndpoint(URL);
        }

        @AfterEach void check(){
            verify(dataStorage, atLeastOnce()).removeEndpoint(URL);
        }
    }
}
