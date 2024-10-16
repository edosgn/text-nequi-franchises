package co.com.nequi.model.product.gateways;

import co.com.nequi.model.product.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ProductRepository {
    Mono<Product> saveProduct(Product product);
    Mono<Product> getProductById(Long id);
    Flux<Product> getAllProducts();
    Mono<Product> updateProduct(Product product);
    Flux<Product> getProductWithMaxStockByBranchId(Long branchId);
    Mono<Void> deleteProduct(Long id);
}
