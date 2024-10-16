package co.com.nequi.r2dbc;

import co.com.nequi.model.product.Product;
import co.com.nequi.model.product.gateways.ProductRepository;
import co.com.nequi.r2dbc.helper.ProductEntityMapperImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class ProductReactiveRepositoryAdapter implements ProductRepository {
    private final ProductReactiveRepository productReactiveRepository;
    private final ProductEntityMapperImpl productEntityMapper;

    @Override
    public Mono<Product> saveProduct(Product product) {
        return productReactiveRepository.save(productEntityMapper.toEntity(product))
                .map(productEntityMapper::toDomain);
    }

    @Override
    public Mono<Product> getProductById(Long id) {
        return productReactiveRepository.findById(id)
                .map(productEntityMapper::toDomain);
    }

    @Override
    public Flux<Product> getAllProducts() {
        return productReactiveRepository.findAll()
                .map(productEntityMapper::toDomain);
    }

    @Override
    public Mono<Product> updateProduct(Product product) {
        return productReactiveRepository.save(productEntityMapper.toEntity(product))
                .map(productEntityMapper::toDomain);
    }

    @Override
    public Flux<Product> getProductWithMaxStockByBranchId(Long branchId) {
        return productReactiveRepository.getProductWithMaxStockByBranchId(branchId)
                .map(productEntityMapper::toDomain);
    }

    @Override
    public Mono<Void> deleteProduct(Long id) {
        return productReactiveRepository.deleteById(id);
    }
}
