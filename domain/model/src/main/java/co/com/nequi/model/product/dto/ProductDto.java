package co.com.nequi.model.product.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record ProductDto(Long id, String name, Integer stock) {
}
