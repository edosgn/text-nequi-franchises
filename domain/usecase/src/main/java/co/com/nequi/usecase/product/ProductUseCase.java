package co.com.nequi.usecase.product;

import co.com.nequi.model.exception.InternalServerErrorException;
import co.com.nequi.model.exception.NotFoundException;
import co.com.nequi.model.product.Product;
import co.com.nequi.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductUseCase {
    public final ProductRepository productRepository;

    public Mono<Product> saveProduct(Product product) {
        return productRepository.saveProduct(product)
                .onErrorMap(e -> new InternalServerErrorException());
    }

    public Mono<Product> getProductById(Long id) {
        return productRepository.getProductById(id)
                .switchIfEmpty(Mono.error(new NotFoundException()))
                .onErrorMap(e -> new InternalServerErrorException());
    }

    public Flux<Product> getAllProducts() {
        return productRepository.getAllProducts()
                .switchIfEmpty(Mono.error(new NotFoundException()))
                .onErrorMap(e -> new InternalServerErrorException());
    }

    public Mono<Product> updateProduct(Product product) {
        return productRepository.updateProduct(product)
                .onErrorMap(e -> new InternalServerErrorException());
    }

    public Flux<Product> getProductWithMaxStockByBranchId(Long branchId) {
        return productRepository.getProductWithMaxStockByBranchId(branchId)
                .onErrorMap(e -> new InternalServerErrorException());
    }

    public Mono<Void> deleteProduct(Long id) {
        return productRepository.deleteProduct(id)
                .switchIfEmpty(Mono.error(new NotFoundException()))
                .onErrorMap(e -> new InternalServerErrorException());
    }
}
