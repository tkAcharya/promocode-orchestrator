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

import com.promocode.orchestrator.contracts.model.CartPricingDto;
import com.promocode.orchestrator.contracts.model.ProductDto;
import com.promocode.orchestrator.contracts.service.PromoCodeProcessor;
import com.promocode.orchestrator.factory.PromoCodeProcessorFactory;
import com.promocode.orchestrator.model.dto.CartViewPageRequest;
import com.promocode.orchestrator.model.dto.ProceedToBuyPageResponse;
import com.promocode.orchestrator.service.CartPricingProcessor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CartPricingProcessorImpl implements CartPricingProcessor {

    private final PromoCodeProcessorFactory promoCodeProcessorFactory;

    public CartPricingProcessorImpl(PromoCodeProcessorFactory promoCodeProcessorFactory) {
        this.promoCodeProcessorFactory = promoCodeProcessorFactory;
    }


    @Override
    public ProceedToBuyPageResponse processViewCartPage(
            @NonNull final CartViewPageRequest cartViewPageRequest) {

        CartPricingDto cartPricingDto = mapCartRequestToCartPricingDto(cartViewPageRequest);

        for (PromoCodeProcessor processor : promoCodeProcessorFactory.getPromoCodeProcessorList()) {
            cartPricingDto = processor.processPromo(cartPricingDto);
        }

        Float finalAmount = 0f;
        for (ProductDto productDto : cartPricingDto.getProductDtoList()) {
            finalAmount += productDto.getOfferPrice();
        }

        if(cartPricingDto.getCartDiscountPct()>0 && cartPricingDto.getCartDiscountPct()<100)
            finalAmount = (finalAmount * (100-cartPricingDto.getCartDiscountPct()))/100;

        return mapCartPricingDtoToProceedToBuyPageResponse(cartPricingDto, finalAmount);

    }

    private CartPricingDto mapCartRequestToCartPricingDto(
            @NonNull final CartViewPageRequest cartViewPageRequest) {
        return CartPricingDto.builder()
                .cartDiscountPct(0f)
                .appliedCartDiscounts(new ArrayList<>())
                .productDtoList(cartViewPageRequest.getProductDtoList())
                .build();
    }

    private ProceedToBuyPageResponse mapCartPricingDtoToProceedToBuyPageResponse(
            @NonNull final CartPricingDto cartPricingDto, final float finalAmount) {
        return ProceedToBuyPageResponse.builder()
                .productDtoList(cartPricingDto.getProductDtoList())
                .finalDiscountPct(cartPricingDto.getCartDiscountPct())
                .finalAmount(finalAmount)
                .actualMrp(cartPricingDto.getMrp())
                .appliedDiscounts(cartPricingDto.getAppliedCartDiscounts())
                .build();
    }

}
