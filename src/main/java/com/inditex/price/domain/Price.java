package com.inditex.price.domain;

import java.math.BigDecimal;

public record Price(BigDecimal value, Currency currency) {
    public Price {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("value must be greater than 0");
        }
        if (currency == null) {
            throw new IllegalArgumentException("currency is required");
        }
    }
}
