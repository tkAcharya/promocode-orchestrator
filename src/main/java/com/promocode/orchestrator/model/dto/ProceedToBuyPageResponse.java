package com.promocode.orchestrator.model.dto;

import com.promocode.orchestrator.contracts.model.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class ProceedToBuyPageResponse {

    private List<ProductDto> productDtoList;
    private float actualMrp;
    private float finalAmount;
    private float finalDiscountPct;
    private List<String> appliedDiscounts;
}
