package co.com.nequi.usecase.product;

import co.com.nequi.model.exception.InternalServerErrorException;
import co.com.nequi.model.exception.NotFoundException;
import co.com.nequi.model.product.Product;
import co.com.nequi.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.PrintWriter;
import java.io.StringWriter;

@RequiredArgsConstructor
public class ProductUseCase {
    public final ProductRepository productRepository;

    public Mono<Product> saveProduct(Product product) {
        return productRepository.saveProduct(product)
                .doOnError(e -> System.err.println("Error saving product: " + e.getMessage()))
                .onErrorMap(e -> new InternalServerErrorException());
    }

    public Mono<Product> getProductById(Long id) {
        return productRepository.getProductById(id)
                .switchIfEmpty(Mono.error(new NotFoundException()))
                .doOnError(e -> System.err.println("Error getting product by id: " + e.getMessage()))
                .onErrorMap(e -> {
                    if (e instanceof NotFoundException) {
                        return e;
                    }
                    return new InternalServerErrorException();
                });
    }

    public Flux<Product> getAllProducts() {
        return productRepository.getAllProducts()
                .switchIfEmpty(Mono.error(new NotFoundException()))
                .doOnError(e -> {
                    System.err.println("Error getting all products: " + e.getMessage());
                })
                .onErrorMap(e -> {
                    if (e instanceof NotFoundException) {
                        return e;
                    }
                    return new InternalServerErrorException();
                });
    }

    public Mono<Product> updateProduct(Product product) {
        return productRepository.updateProduct(product)
                .doOnError(e -> System.err.println("Error updating product: " + e.getMessage()))
                .onErrorMap(e -> new InternalServerErrorException());
    }

    public Flux<Product> getProductWithMaxStockByBranchId(Long branchId) {
        return productRepository.getProductWithMaxStockByBranchId(branchId)
                .doOnError(e -> System.err.println("Error getting product with max stock by branch id: " + e.getMessage()))
                .onErrorMap(e -> new InternalServerErrorException());
    }

    public Mono<Void> deleteProduct(Long id) {
        return productRepository.deleteProduct(id)
                .doOnError(e -> System.err.println("Error deleting product: " + e.getMessage()))
                .onErrorMap(e -> new InternalServerErrorException());
    }

    public static String getStackTraceAsString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
