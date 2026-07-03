package com.inditex.price.adapter.in.api;

import com.inditex.price.adapter.in.api.model.GetPriceResponse;
import com.inditex.price.adapter.in.map.PriceMapperIn;
import com.inditex.price.application.model.ApplicablePriceCommand;
import com.inditex.price.application.port.in.FindApplicablePriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PriceController implements PricesApi {

    private final FindApplicablePriceService findApplicablePriceService;
    private final PriceMapperIn priceMapperIn;

    @Override
    public ResponseEntity<GetPriceResponse> getApplicablePrice(
            String applicationDate,
            Long productId,
            Integer brandId) {

        log.info("Request received to get applicable price for product {}.", productId);
        GetPriceResponse getPriceResponse = priceMapperIn.toResponse(
                findApplicablePriceService.findByFilter(
                        new ApplicablePriceCommand(applicationDate, productId, brandId)
                )
        );

        log.info("Applicable price {} found for product {}.", getPriceResponse.getPrice(), productId);

        return ResponseEntity.ok(getPriceResponse);
    }
}
