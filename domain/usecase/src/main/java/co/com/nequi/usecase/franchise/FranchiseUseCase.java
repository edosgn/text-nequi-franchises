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

    public Mono<Franchise> saveFranchise(Franchise franchise) {
        return franchiseRepository.saveFranchise(franchise)
                .doOnError(e -> System.err.println("Error saving franchise: " + e.getMessage()))
                .onErrorMap(e -> new InternalServerErrorException());
    }

    public Flux<Franchise> getAllFranchises() {
        return franchiseRepository.getAllFranchises()
                .switchIfEmpty(Mono.error(new NotFoundException()))
                .doOnError(e -> System.err.println("Error getting all franchises: " + e.getMessage()))
                .onErrorMap(e -> {
                    if (e instanceof NotFoundException) {
                        return e;
                    }
                    return new InternalServerErrorException();
                });
    }

    public Mono<Franchise> getById(Long id) {
        return franchiseRepository.getById(id)
                .switchIfEmpty(Mono.error(new NotFoundException()))
                .doOnError(e -> System.err.println("Error getting franchise by id: " + e.getMessage()))
                .onErrorMap(e -> {
                    if (e instanceof NotFoundException) {
                        return e;
                    }
                    return new InternalServerErrorException();
                });
    }

    public Mono<Void> updateFranchise(Franchise franchise) {
        return franchiseRepository.getById(franchise.getId())
            .switchIfEmpty(Mono.error(new NotFoundException()))
            .flatMap(existingFranchise -> franchiseRepository.updateFranchise(franchise))
                .doOnError(e -> System.err.println("Error updating franchise: " + e.getMessage()))
            .onErrorMap(e -> {
                if (e instanceof NotFoundException) {
                    return e;
                }
                return new InternalServerErrorException();
            });
    }
}
