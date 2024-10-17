package co.com.nequi.r2dbc;

import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.model.franchise.gateways.FranchiseRepository;

import co.com.nequi.r2dbc.helper.FranchiseEntityMapperImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class FranchiseReactiveRepositoryAdapter  implements FranchiseRepository {
    private final FranchiseReactiveRepository franchiseReactiveRepository;
    private final FranchiseEntityMapperImpl franchiseEntityMapper;

    @Override
    public Flux<Franchise> getAllFranchises() {
        return franchiseReactiveRepository.findAll()
                .map(franchiseEntityMapper::toDomain);
    }

    @Override
    public Mono<Franchise> saveFranchise(Franchise franchise) {
        return franchiseReactiveRepository.save(franchiseEntityMapper.toEntity(franchise))
                .map(franchiseEntityMapper::toDomain);
    }

    @Override
    public Mono<Franchise> getById(Long id) {
        return franchiseReactiveRepository.findById(id)
                .map(franchiseEntityMapper::toDomain);
    }

    @Override
    public Mono<Void> updateFranchise(Franchise franchise) {
        return franchiseReactiveRepository.save(franchiseEntityMapper.toEntity(franchise))
                .then();
    }
}
