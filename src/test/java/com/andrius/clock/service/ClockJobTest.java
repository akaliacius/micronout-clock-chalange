package com.andrius.clock.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class ClockJobTest {
    private ClockJob testable;
    private DataStorage storageMock;

    @BeforeEach void setup(){
        testable = new ClockJob(mock(DataStorage.class));
    }

    @ParameterizedTest
    @ValueSource(ints = {6, 8, 13000, 5000, 1987})
    void numbers_within_range(int seconds) throws Exception {
        testable.update(seconds);
        assertEquals(seconds, getFrequencySec());
    }

    @ParameterizedTest
    @ValueSource(ints = {40000, 458000, 20000, 15897, 66555})
    void numbers_above_range(int seconds) throws Exception {
        assertThrows(IllegalArgumentException.class, () -> testable.update(seconds));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, -458, 0, -20000, -15897, -66555})
    void numbers_below_range(int seconds) throws Exception {
        assertThrows(IllegalArgumentException.class, () -> testable.update(seconds));
    }

    private int getFrequencySec() throws Exception {
        Field field = testable.getClass().getDeclaredField("frequencySec");
        field.setAccessible(true);
        return (int) field.get(testable);
    }
}
