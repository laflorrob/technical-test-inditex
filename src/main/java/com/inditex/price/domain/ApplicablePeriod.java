package com.inditex.price.domain;

import java.time.LocalDateTime;

public record ApplicablePeriod(LocalDateTime startDate, LocalDateTime endDate) {
    public ApplicablePeriod {
        if (startDate == null) {
            throw new IllegalArgumentException("startDate is required");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("endDate is required");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("endDate must be after startDate");
        }
    }
}
