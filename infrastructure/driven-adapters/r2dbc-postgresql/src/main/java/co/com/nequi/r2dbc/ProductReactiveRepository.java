package co.com.nequi.r2dbc;

import co.com.nequi.r2dbc.entity.ProductEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProductReactiveRepository extends ReactiveCrudRepository<ProductEntity, Long> {
    @Query("SELECT * FROM product WHERE branch_id = :branchId ORDER BY stock DESC")
    Flux<ProductEntity> getProductWithMaxStockByBranchId(Long branchId);
}
