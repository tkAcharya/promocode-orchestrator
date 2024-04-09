/*
 * MIT License
 *
 * Copyright (c) 2024 TARUN KUMAR ACHARYA
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.promocode.orchestrator.service.impl;

import com.promocode.orchestrator.contracts.model.ProductDto;
import com.promocode.orchestrator.contracts.service.PromoCodeProcessor;
import com.promocode.orchestrator.factory.PromoCodeProcessorFactory;
import com.promocode.orchestrator.model.dto.CartViewPageRequest;
import com.promocode.orchestrator.model.dto.ProceedToBuyPageResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class CartPricingProcessorImplTest {

    CartPricingProcessorImpl cartPricingProcessorImpl;

    @BeforeEach
    public void setUp() {
        ProductLevelPromoCodeProcessorImpl productLevelPromoCodeProcessorImpl
                = new ProductLevelPromoCodeProcessorImpl();
        List<PromoCodeProcessor> promoCodeProcessorList = new ArrayList<>();
        promoCodeProcessorList.add(productLevelPromoCodeProcessorImpl);

        cartPricingProcessorImpl =
                new CartPricingProcessorImpl(
                        new PromoCodeProcessorFactory(promoCodeProcessorList)
                );
    }

    @Test
    void processViewCartPage() {

        ProductDto productDto1 = ProductDto.builder()
                .productId("1001")
                .actualMrp(1200)
                .offerPrice(1200)
                .build();
        ProductDto productDto2 = ProductDto.builder()
                .productId("1002")
                .actualMrp(1000)
                .offerPrice(1000)
                .build();
        List<ProductDto> productDtoList = new ArrayList<>();
        productDtoList.add(productDto1);
        productDtoList.add(productDto2);
        CartViewPageRequest cartViewPageRequest = CartViewPageRequest.builder()
                .productDtoList(productDtoList)
                .build();

        ProceedToBuyPageResponse proceedToBuyPageResponse =
                cartPricingProcessorImpl.processViewCartPage(cartViewPageRequest);

        Assertions.assertNotNull(proceedToBuyPageResponse);
        Assertions.assertEquals(2140, proceedToBuyPageResponse.getFinalAmount());

    }
}