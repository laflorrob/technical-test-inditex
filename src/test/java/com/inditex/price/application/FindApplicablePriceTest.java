package com.inditex.price.application;

import com.inditex.price.application.exception.PriceNotFoundException;
import com.inditex.price.application.model.ApplicablePriceCommand;
import com.inditex.price.application.port.out.PricePersistenceService;
import com.inditex.price.domain.ApplicablePeriod;
import com.inditex.price.domain.ApplicablePrice;
import com.inditex.price.domain.Currency;
import com.inditex.price.domain.Price;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindApplicablePriceTest {

    @InjectMocks
    private FindApplicablePrice findApplicablePrice;

    @Mock
    private PricePersistenceService pricePersistenceService;

    private static final ApplicablePriceCommand FILTER = new ApplicablePriceCommand("2020-06-14-10.00.00", 35455L, 1);

    @Test
    void shouldReturnPrice_whenSingleApplicablePriceExists() {
        ApplicablePrice expected = priceWithPriority(3);

        when(pricePersistenceService.findApplicablePrices(any())).thenReturn(List.of(expected));

        ApplicablePrice result = findApplicablePrice.findByFilter(FILTER);

        assertSame(expected, result);
    }

    @Test
    void shouldReturnHighestPriorityPrice_whenMoreThanOneApplicablePrice() {
        ApplicablePrice lowerPriority = priceWithPriority(1);
        ApplicablePrice higherPriority = priceWithPriority(5);

        when(pricePersistenceService.findApplicablePrices(any())).thenReturn(List.of(lowerPriority, higherPriority));

        ApplicablePrice result = findApplicablePrice.findByFilter(FILTER);

        assertSame(higherPriority, result);
    }

    @Test
    void shouldThrowPriceNotFoundException_whenNoApplicablePricesFound() {
        PriceNotFoundException exception = assertThrows(
                PriceNotFoundException.class,
                () -> findApplicablePrice.findByFilter(FILTER)
        );

        assertEquals("No applicable price found for the given filter", exception.getMessage());
    }

    private static ApplicablePrice priceWithPriority(int priority) {
        return new ApplicablePrice(
                1L,
                1,
                new ApplicablePeriod(
                        LocalDateTime.now().minus(2, TimeUnit.HOURS.toChronoUnit()),
                        LocalDateTime.now().plus(3, TimeUnit.HOURS.toChronoUnit())
                ),
                1,
                1L,
                priority,
                new Price(BigDecimal.valueOf(20.0), Currency.EUR)
        );
    }
}

