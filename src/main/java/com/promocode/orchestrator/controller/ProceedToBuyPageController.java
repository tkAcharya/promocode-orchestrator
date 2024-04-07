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

package com.promocode.orchestrator.controller;

import com.promocode.orchestrator.contracts.model.CartPricingDto;
import com.promocode.orchestrator.model.dto.CartViewPageRequest;
import com.promocode.orchestrator.model.dto.ProceedToBuyPageResponse;
import com.promocode.orchestrator.service.CartPricingProcessor;
import com.promocode.orchestrator.service.impl.CartPricingProcessorImpl;
import org.apache.catalina.connector.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/cart")
public class ProceedToBuyPageController {

    private final CartPricingProcessorImpl cartPricingProcessorImpl;

    public ProceedToBuyPageController(CartPricingProcessorImpl cartPricingProcessorImpl) {
        this.cartPricingProcessorImpl = cartPricingProcessorImpl;
    }

    @PostMapping("/proceed-to-buy")
    public ProceedToBuyPageResponse proceedToBuy(@RequestBody CartViewPageRequest cartViewPageRequest){
        return cartPricingProcessorImpl.processViewCartPage(cartViewPageRequest);
    }
}
