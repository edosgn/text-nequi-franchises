package co.com.nequi.api;

import co.com.nequi.model.exception.InternalServerErrorException;
import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.usecase.franchise.FranchiseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FranchiseHandler {
    private final FranchiseUseCase franchiseUseCase;

    public Mono<ServerResponse> createFranchise(ServerRequest request) {
        return request.bodyToMono(Franchise.class)
                .flatMap(franchiseUseCase::saveFranchise)
                .flatMap(franchise -> ServerResponse.ok().bodyValue(franchise))
                .onErrorResume(InternalServerErrorException.class, e -> ServerResponse.status(500).build());
    }

    public Mono<ServerResponse> getFranchiseById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return franchiseUseCase.getById(id)
                .flatMap(franchise -> ServerResponse.ok().bodyValue(franchise))
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(InternalServerErrorException.class, e -> ServerResponse.status(500).build());
    }

    public Mono<ServerResponse> getAllFranchises(ServerRequest request) {
        return franchiseUseCase.getAllFranchises()
                .collectList()
                .flatMap(franchises -> ServerResponse.ok().bodyValue(franchises))
                .onErrorResume(InternalServerErrorException.class, e -> ServerResponse.status(500).build());
    }

    public Mono<ServerResponse> updateFranchise(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(Franchise.class)
                .flatMap(franchise -> {
                    franchise.setId(id);
                    return franchiseUseCase.updateFranchise(franchise);
                })
                .flatMap(franchise -> ServerResponse.ok().bodyValue(franchise))
                .onErrorResume(InternalServerErrorException.class, e -> ServerResponse.status(500).build());
    }
}
