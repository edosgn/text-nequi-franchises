package co.com.nequi.r2dbc;

import co.com.nequi.r2dbc.entity.FranchiseEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface FranchiseReactiveRepository extends ReactiveCrudRepository<FranchiseEntity, Long> {
}
