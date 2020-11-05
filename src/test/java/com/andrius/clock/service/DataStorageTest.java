package com.andrius.clock.service;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class DataStorageTest {

    public static final String URL = "https://www.example.com/boundary/aunt";

    @Inject
    private DataStorage testable;

    @BeforeEach void setup(){
        testable.reset();
        IntStream.range(0, 100).forEach(i -> {
            testable.insertEndpoint(URL + i);
        });
        testable.insertEndpoint(URL);
        assertEquals(101, testable.listAll().stream().count());
    }

    @Test void should_return_true_while_inserting_non_existing(){
        assertTrue(testable.insertEndpoint(URL + UUID.randomUUID().toString()));
        assertEquals(102, testable.listAll().stream().count());
    }

    @Test void should_return_false_while_inserting_existing(){
        assertFalse(testable.insertEndpoint(URL));
        assertEquals(101, testable.listAll().stream().count());
    }

    @Test void should_return_true_while_removing_existing(){
        assertTrue(testable.removeEndpoint(URL));
        assertEquals(100, testable.listAll().stream().count());
    }

    @Test void should_return_false_while_removing_non_existing(){
        assertFalse(testable.removeEndpoint(URL+ UUID.randomUUID().toString()));
        assertEquals(101, testable.listAll().stream().count());
    }
}
