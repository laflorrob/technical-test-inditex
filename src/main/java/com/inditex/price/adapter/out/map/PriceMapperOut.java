package com.inditex.price.adapter.out.map;

import com.inditex.price.adapter.out.persistence.PriceEntity;
import com.inditex.price.domain.ApplicablePrice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceMapperOut {

	@Mapping(target = "applicablePeriod.startDate", source = "startDate")
	@Mapping(target = "applicablePeriod.endDate", source = "endDate")
	@Mapping(target = "price.value", source = "price")
	@Mapping(target = "price.currency", source = "currency")
	ApplicablePrice toDomain(PriceEntity entity);
}
