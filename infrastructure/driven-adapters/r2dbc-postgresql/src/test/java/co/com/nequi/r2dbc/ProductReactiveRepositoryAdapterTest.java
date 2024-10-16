package co.com.nequi.r2dbc;

import co.com.nequi.model.product.Product;
import co.com.nequi.r2dbc.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductReactiveRepositoryAdapterTest {
    // TODO: change four you own tests

    @InjectMocks
    ProductReactiveRepositoryAdapter repositoryAdapter;

    @Mock
    ProductReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

    @Test
    void mustFindValueById() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(1L);
        productEntity.setName("test");

        Product product = new Product();
        product.setId(1L);
        product.setName("test");

        when(repository.findById("1")).thenReturn(Mono.just(productEntity));
        when(mapper.map(productEntity, Product.class)).thenReturn(product);

        Mono<Product> result = repositoryAdapter.getProductById(1L);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getName().equals("test"))
                .verifyComplete();
    }

    @Test
    void mustFindAllValues() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName("test");

        Product product = new Product();
        product.setName("test");

        when(repository.findAll()).thenReturn(Flux.just(productEntity));
        when(mapper.map(productEntity, Product.class)).thenReturn(product);

        Flux<Product> result = repositoryAdapter.getAllProducts();

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getName().equals("test"))
                .verifyComplete();
    }

    @Test
    void mustSaveValue() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName("test");

        Product product = new Product();
        product.setName("test");

        when(repository.save(productEntity)).thenReturn(Mono.just(productEntity));
        when(mapper.map(productEntity, Product.class)).thenReturn(product);
        when(mapper.map(product, ProductEntity.class)).thenReturn(productEntity);

        Mono<Product> result = repositoryAdapter.saveProduct(product);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getName().equals("test"))
                .verifyComplete();
    }
}
