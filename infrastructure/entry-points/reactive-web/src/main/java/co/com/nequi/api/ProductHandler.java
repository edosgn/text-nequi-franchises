package co.com.nequi.api;

import co.com.nequi.model.exception.InternalServerErrorException;
import co.com.nequi.model.product.Product;
import co.com.nequi.usecase.product.ProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductHandler {
    private final ProductUseCase productUseCase;

    public Mono<ServerResponse> createProduct(ServerRequest request) {
        return request.bodyToMono(Product.class)
                .flatMap(productUseCase::saveProduct)
                .flatMap(product -> ServerResponse.ok().bodyValue(product))
                .onErrorResume(InternalServerErrorException.class, e -> ServerResponse.status(500).build());
    }

    public Mono<ServerResponse> getProductById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return productUseCase.getProductById(id)
                .flatMap(product -> ServerResponse.ok().bodyValue(product))
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(InternalServerErrorException.class, e -> ServerResponse.status(500).build());
    }

    public Mono<ServerResponse> getAllProducts(ServerRequest request) {
        return productUseCase.getAllProducts()
                .collectList()
                .flatMap(products -> ServerResponse.ok().bodyValue(products))
                .onErrorResume(InternalServerErrorException.class, e -> ServerResponse.status(500).build());
    }

    public Mono<ServerResponse> updateProduct(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(Product.class)
                .flatMap(product -> {
                    product.setId(id);
                    return productUseCase.updateProduct(product);
                })
                .flatMap(product -> ServerResponse.ok().bodyValue(product))
                .onErrorResume(InternalServerErrorException.class, e -> ServerResponse.status(500).build());
    }

    public Flux<ServerResponse> getProductWithMaxStockByBranchId(ServerRequest request) {
        Long branchId = Long.valueOf(request.pathVariable("branchId"));
        return productUseCase.getProductWithMaxStockByBranchId(branchId)
                .flatMap(product -> ServerResponse.ok().bodyValue(product))
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(InternalServerErrorException.class, e -> ServerResponse.status(500).build());
    }
}
