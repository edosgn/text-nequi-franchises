package co.com.nequi.r2dbc.helper;

import co.com.nequi.model.product.Product;
import co.com.nequi.r2dbc.entity.ProductEntity;

public interface ProductEntityMapper {
    ProductEntity toEntity(Product product);

    Product toDomain(ProductEntity productEntity);
}
