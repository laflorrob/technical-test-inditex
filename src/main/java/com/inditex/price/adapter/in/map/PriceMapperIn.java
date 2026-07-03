package com.inditex.price.adapter.in.map;

import com.inditex.price.adapter.in.api.model.GetPriceResponse;
import com.inditex.price.domain.ApplicablePrice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceMapperIn {

	@Mapping(target = "startDate", source = "applicablePeriod.startDate", dateFormat = "yyyy-MM-dd-HH.mm.ss")
	@Mapping(target = "endDate", source = "applicablePeriod.endDate", dateFormat = "yyyy-MM-dd-HH.mm.ss")
	@Mapping(target = "price", source = "price.value")
	@Mapping(target = "currency", source = "price.currency")
	GetPriceResponse toResponse(ApplicablePrice applicablePrice);

}
