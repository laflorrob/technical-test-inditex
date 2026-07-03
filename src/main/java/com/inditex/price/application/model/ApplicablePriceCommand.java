package com.inditex.price.application.model;

public record ApplicablePriceCommand(
    String applicationDate,
    Long productId,
    Integer brandId
) {}
