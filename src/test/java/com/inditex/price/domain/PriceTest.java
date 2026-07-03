package com.inditex.price.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PriceTest {

    @Test
    void shouldCreatePrice_whenInputIsValid() {
        Price price = new Price(new BigDecimal("35.50"), Currency.EUR);

        assertEquals(new BigDecimal("35.50"), price.value());
        assertEquals(Currency.EUR, price.currency());
    }

    @Test
    void shouldFail_whenValueIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Price(null, Currency.EUR)
        );

        assertEquals("value must be greater than 0", exception.getMessage());
    }

    @Test
    void shouldFail_whenValueIsZero() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Price(BigDecimal.ZERO, Currency.EUR)
        );

        assertEquals("value must be greater than 0", exception.getMessage());
    }

    @Test
    void shouldFail_whenValueIsNegative() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Price(new BigDecimal("-1.00"), Currency.EUR)
        );

        assertEquals("value must be greater than 0", exception.getMessage());
    }

    @Test
    void shouldFail_whenCurrencyIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Price(new BigDecimal("10.00"), null)
        );

        assertEquals("currency is required", exception.getMessage());
    }
}

