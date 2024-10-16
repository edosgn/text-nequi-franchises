package co.com.nequi.usecase.franchise;

import co.com.nequi.model.exception.InternalServerErrorException;
import co.com.nequi.model.exception.NotFoundException;
import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.model.franchise.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FranchiseUseCase {
    public final FranchiseRepository franchiseRepository;

    public Flux<Franchise> getAllFranchises() {
        return franchiseRepository.getAllFranchises()
                .switchIfEmpty(Mono.error(new NotFoundException()))
                .onErrorMap(e -> new InternalServerErrorException());
    }

    public Mono<Void> saveFranchise(Franchise franchise) {
        return franchiseRepository.saveFranchise(franchise)
                .onErrorMap(e -> new InternalServerErrorException());
    }

    public Mono<Franchise> getById(Long id) {
        return franchiseRepository.getById(id)
                .switchIfEmpty(Mono.error(new NotFoundException()))
                .onErrorMap(e -> new InternalServerErrorException());
    }

    public Mono<Void> updateFranchise(Franchise franchise) {
        return franchiseRepository.updateFranchise(franchise)
                .onErrorMap(e -> new InternalServerErrorException());
    }
}
