package co.com.nequi.model.branch.dto;

import co.com.nequi.model.product.dto.ProductDto;
import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record BranchDto(Long id, String name, Long franchiseId, List<ProductDto> productList) {
}
