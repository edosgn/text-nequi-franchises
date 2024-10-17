package co.com.nequi.api;

import co.com.nequi.api.dto.ProductRequest;
import co.com.nequi.model.exception.InternalServerErrorException;
import co.com.nequi.model.exception.NotFoundException;
import co.com.nequi.model.product.Product;
import co.com.nequi.usecase.product.ProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductHandler {
    private final ProductUseCase productUseCase;

    public Mono<ServerResponse> createProduct(ServerRequest request) {
        return request.bodyToMono(Product.class)
                .doOnSubscribe(product -> System.out.println("Creating product"))
                .flatMap(productUseCase::saveProduct)
                .flatMap(product -> ServerResponse.ok().bodyValue(product))
                .onErrorResume(InternalServerErrorException.class, e -> ServerResponse.status(500).build());
    }

    public Mono<ServerResponse> getProductById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return productUseCase.getProductById(id)
                .doOnSubscribe(product -> System.out.println("Getting product by id"))
                .flatMap(product -> ServerResponse.ok().bodyValue(product))
                .onErrorResume(e -> {
                    if (e instanceof NotFoundException) {
                        return ServerResponse.notFound().build();
                    } else {
                        return ServerResponse.status(500).build(); // Default case for other exceptions
                    }
                });
    }

    public Mono<ServerResponse> getAllProducts(ServerRequest request) {
        return productUseCase.getAllProducts()
                .collectList()
                .doOnSubscribe(products -> System.out.println("Getting all products"))
                .flatMap(products -> ServerResponse.ok().bodyValue(products))
                .onErrorResume(e -> {
                    if (e instanceof NotFoundException) {
                        return ServerResponse.notFound().build();
                    } else {
                        return ServerResponse.status(500).build(); // Default case for other exceptions
                    }
                });
    }

    public Mono<ServerResponse> updateProduct(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(Product.class)
                .doOnSubscribe(product -> System.out.println("Updating product"))
                .flatMap(product -> {
                    product.setId(id);
                    return productUseCase.updateProduct(product);
                })
                .flatMap(product -> ServerResponse.ok().bodyValue(product))
                .onErrorResume(InternalServerErrorException.class, e -> ServerResponse.status(500).build());
    }

    public Mono<ServerResponse> updateNameProduct(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(ProductRequest.class)
                .map(ProductRequest::getName)
                .doOnSubscribe(name -> System.out.println("Updating product name"))
                .flatMap(name -> productUseCase.getProductById(id)
                        .flatMap(product -> {
                            product.setName(name);
                            return productUseCase.updateProduct(product);
                        }))
                .flatMap(product -> ServerResponse.ok().bodyValue(product))
                .onErrorResume(InternalServerErrorException.class, e -> ServerResponse.status(500).build());
    }

    public Mono<ServerResponse> updateStockProduct(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(ProductRequest.class)
                .map(ProductRequest::getStock)
                .doOnSubscribe(stock -> System.out.println("Updating product stock"))
                .flatMap(stock -> productUseCase.getProductById(id)
                        .flatMap(product -> {
                            product.setStock(stock);
                            return productUseCase.updateProduct(product);
                        }))
                .flatMap(product -> ServerResponse.ok().bodyValue(product))
                .onErrorResume(InternalServerErrorException.class, e -> ServerResponse.status(500).build());
    }

    public Mono<ServerResponse> getProductWithMaxStockByBranchId(ServerRequest request) {
        Long branchId = Long.valueOf(request.pathVariable("branchId"));
        return productUseCase.getProductWithMaxStockByBranchId(branchId)
                .collectList()
                .doOnSubscribe(products -> System.out.println("Getting product with max stock by branch id"))
                .flatMap(result -> ServerResponse.ok().bodyValue(result))
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(InternalServerErrorException.class, e -> ServerResponse.status(500).build());
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return productUseCase.deleteProduct(id)
                .doOnSubscribe(product -> System.out.println("Deleting product"))
                .then(ServerResponse.ok().build())
                .onErrorResume(InternalServerErrorException.class, e -> ServerResponse.status(500).build());
    }
}
