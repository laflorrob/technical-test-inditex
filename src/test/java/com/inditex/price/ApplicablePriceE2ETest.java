package com.inditex.price;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ApplicablePriceE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnPriceList_whenRequestIsAt2020_06_14_10_00_00() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("applicationDate", "2020-06-14-10.00.00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.startDate").value("2020-06-14-00.00.00"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31-23.59.59"))
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    void shouldReturnPriceList_whenRequestIsAt2020_06_14_16_00_00() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("applicationDate", "2020-06-14-16.00.00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.startDate").value("2020-06-14-15.00.00"))
                .andExpect(jsonPath("$.endDate").value("2020-06-14-18.30.00"))
                .andExpect(jsonPath("$.price").value(25.45))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    void shouldReturnPriceList1_whenRequestIsAt2020_06_14_21_00_00() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("applicationDate", "2020-06-14-21.00.00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    void shouldReturnPriceList3_whenRequestIsAt2020_06_15_10_00_00() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("applicationDate", "2020-06-15-10.00.00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(3))
                .andExpect(jsonPath("$.price").value(30.50));
    }

    @Test
    void shouldReturnPriceList4_whenRequestIsAt2020_06_16_21_00_00() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("applicationDate", "2020-06-16-21.00.00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(4))
                .andExpect(jsonPath("$.startDate").value("2020-06-15-16.00.00"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31-23.59.59"))
                .andExpect(jsonPath("$.price").value(38.95));
    }

    @Test
    void shouldReturnNotFound_whenNoPriceMatchesFilter() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("applicationDate", "2020-06-16-21.00.00")
                        .param("productId", "99999")
                        .param("brandId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No applicable price found for the given filter"))
                .andExpect(jsonPath("$.path").value("/prices"));
    }
}

