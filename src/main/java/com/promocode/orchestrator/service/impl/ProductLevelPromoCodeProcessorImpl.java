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
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductLevelPromoCodeProcessorImpl implements PromoCodeProcessor {

    @Override
    public String getPromoCodeProcessorType() {
        return "PRODUCT";
    }

    @Override
    public CartPricingDto processPromo(@NonNull CartPricingDto cartPricingDto) {
        // A sample template function to put 5 percent discount in alternate products
        List<ProductDto> productDtoList = cartPricingDto.getProductDtoList();
        boolean flag = true;
        for(ProductDto productDto : productDtoList){
            if(flag){
                productDto.setOfferPrice((float) (productDto.getOfferPrice()*0.95));
                flag = false;
            } else {
                flag = true;
            }
        }
        return CartPricingDto.builder()
                .cartDiscountPct(cartPricingDto.getCartDiscountPct())
                .mrp(cartPricingDto.getMrp())
                .appliedCartDiscounts(cartPricingDto.getAppliedCartDiscounts())
                .productDtoList(productDtoList)
                .build();
    }
}
