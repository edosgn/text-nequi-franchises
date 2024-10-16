package co.com.nequi.r2dbc;

import co.com.nequi.r2dbc.entity.BranchEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface BranchReactiveRepository extends ReactiveCrudRepository<BranchEntity, Long> {
    @Query("SELECT * FROM branch WHERE franchise_id = :franchiseId")
    Flux<BranchEntity> getBranchesByFranchiseId(Long franchiseId);
}
