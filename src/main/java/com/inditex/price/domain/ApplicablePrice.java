package com.inditex.price.domain;

public record ApplicablePrice(Long id,
							  Integer brandId,
							  ApplicablePeriod applicablePeriod,
							  Integer priceList,
							  Long productId,
							  Integer priority,
							  Price price) {}
