package co.com.nequi.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Setter
@Getter
public class ProductRequest {
    private String name;
    private int stock;
}