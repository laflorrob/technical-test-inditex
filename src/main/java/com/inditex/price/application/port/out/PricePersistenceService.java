package com.inditex.price.application.port.out;

import com.inditex.price.application.model.ApplicablePriceCommand;
import com.inditex.price.domain.ApplicablePrice;

import java.util.List;

public interface PricePersistenceService {

    List<ApplicablePrice> findApplicablePrices(ApplicablePriceCommand filter);

}
