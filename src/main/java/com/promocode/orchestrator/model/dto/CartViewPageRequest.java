package com.promocode.orchestrator.model.dto;

import com.promocode.orchestrator.contracts.model.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class CartViewPageRequest {

    private List<ProductDto> productDtoList;
}
