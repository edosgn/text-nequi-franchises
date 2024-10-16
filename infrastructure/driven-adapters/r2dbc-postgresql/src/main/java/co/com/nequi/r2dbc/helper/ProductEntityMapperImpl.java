package co.com.nequi.r2dbc.helper;

import co.com.nequi.model.product.Product;
import co.com.nequi.r2dbc.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductEntityMapperImpl implements ProductEntityMapper {
    @Override
    public ProductEntity toEntity(Product product) {
        return ProductEntity.builder()
                .id(product.getId())
                .name(product.getName())
                .stock(product.getStock())
                .branchId(product.getBranchId())
                .build();
    }

    @Override
    public Product toDomain(ProductEntity productEntity) {
        return Product.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .stock(productEntity.getStock())
                .branchId(productEntity.getBranchId())
                .build();
    }
}
