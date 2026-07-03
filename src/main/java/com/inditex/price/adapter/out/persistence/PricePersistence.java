package com.inditex.price.adapter.out.persistence;

import com.inditex.price.adapter.out.map.PriceMapperOut;
import com.inditex.price.application.model.ApplicablePriceCommand;
import com.inditex.price.application.port.out.PricePersistenceService;
import com.inditex.price.domain.ApplicablePrice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.inditex.price.adapter.out.persistence.PriceSpecifications.applicationDateBetweenStartAndEnd;
import static com.inditex.price.adapter.out.persistence.PriceSpecifications.hasBrandId;
import static com.inditex.price.adapter.out.persistence.PriceSpecifications.hasProductId;

@Service
@RequiredArgsConstructor
@Slf4j
public class PricePersistence implements PricePersistenceService {

    private static final DateTimeFormatter APPLICATION_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");

    private final PriceRepository priceRepository;
    private final PriceMapperOut priceMapper;

    @Override
    public List<ApplicablePrice> findApplicablePrices(ApplicablePriceCommand filter) {
        LocalDateTime applicationDate = LocalDateTime.parse(filter.applicationDate(), APPLICATION_DATE_FORMATTER);

        log.info("Retrieving applicable prices for product {}, brand {} and date {}.",
                filter.productId(), filter.brandId(), applicationDate);

        return priceRepository.findAll(
                hasBrandId(filter.brandId())
                        .and(hasProductId(filter.productId()))
                        .and(applicationDateBetweenStartAndEnd(applicationDate))
                )
                .stream()
                .map(priceMapper::toDomain)
                .toList();
    }
}
