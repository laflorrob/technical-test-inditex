package com.inditex.price.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApplicablePeriodTest {

    private static final LocalDateTime START = LocalDateTime.of(2020, 6, 14, 0, 0, 0);
    private static final LocalDateTime END   = LocalDateTime.of(2020, 12, 31, 23, 59, 59);

    @Test
    void shouldCreateApplicablePeriod_whenInputIsValid() {
        ApplicablePeriod period = new ApplicablePeriod(START, END);

        assertEquals(START, period.startDate());
        assertEquals(END, period.endDate());
    }

    @Test
    void shouldCreateApplicablePeriod_whenStartAndEndAreEqual() {
        ApplicablePeriod period = new ApplicablePeriod(START, START);

        assertEquals(START, period.startDate());
        assertEquals(START, period.endDate());
    }

    @Test
    void shouldFail_whenStartDateIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new ApplicablePeriod(null, END)
        );

        assertEquals("startDate is required", exception.getMessage());
    }

    @Test
    void shouldFail_whenEndDateIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new ApplicablePeriod(START, null)
        );

        assertEquals("endDate is required", exception.getMessage());
    }

    @Test
    void shouldFail_whenEndDateIsBeforeStartDate() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new ApplicablePeriod(END, START)
        );

        assertEquals("endDate must be after startDate", exception.getMessage());
    }

    @Test
    void shouldFail_whenEndDateIsOneSecondBeforeStartDate() {
        LocalDateTime oneSecondBefore = START.minusSeconds(1);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new ApplicablePeriod(START, oneSecondBefore)
        );

        assertEquals("endDate must be after startDate", exception.getMessage());
    }
}

