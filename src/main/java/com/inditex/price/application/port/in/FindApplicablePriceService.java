package com.inditex.price.application.port.in;

import com.inditex.price.application.model.ApplicablePriceCommand;
import com.inditex.price.domain.ApplicablePrice;

public interface FindApplicablePriceService {

    ApplicablePrice findByFilter(ApplicablePriceCommand filter);

}
