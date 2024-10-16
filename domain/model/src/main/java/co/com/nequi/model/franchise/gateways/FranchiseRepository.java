package co.com.nequi.model.franchise.gateways;

import co.com.nequi.model.franchise.Franchise;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranchiseRepository {
    Flux<Franchise> getAllFranchises();
    Mono<Void> saveFranchise(Franchise franchise);
    Mono<Franchise> getById(Long id);
    Mono<Void> updateFranchise(Franchise franchise);
}
