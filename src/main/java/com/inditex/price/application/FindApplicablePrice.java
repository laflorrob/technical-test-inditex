package com.inditex.price.application;

import com.inditex.price.application.exception.PriceNotFoundException;
import com.inditex.price.application.model.ApplicablePriceCommand;
import com.inditex.price.application.port.in.FindApplicablePriceService;
import com.inditex.price.domain.ApplicablePrice;

import com.inditex.price.application.port.out.PricePersistenceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindApplicablePrice implements FindApplicablePriceService {

    private final PricePersistenceService pricePersistenceService;

    @Override
    public ApplicablePrice findByFilter(ApplicablePriceCommand filter) {
        log.info("Finding applicable price for product {}.", filter.productId());
        return pricePersistenceService.findApplicablePrices(filter)
                .stream()
                .reduce((left, right) -> left.priority() >= right.priority() ? left : right)
                .orElseThrow(() -> {
                    log.info("No applicable price found for the given filter");
                    return new PriceNotFoundException("No applicable price found for the given filter");
                });
    }
}

