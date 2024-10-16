package co.com.nequi.api.dto;

import lombok.Builder;

@Builder
public record ProductRequest(
        String name,
        int stock
) {
}
