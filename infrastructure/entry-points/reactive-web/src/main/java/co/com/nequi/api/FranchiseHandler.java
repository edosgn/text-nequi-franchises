package co.com.nequi.api;

import co.com.nequi.api.dto.BranchRequest;
import co.com.nequi.api.dto.FranchiseRequest;
import co.com.nequi.model.exception.InternalServerErrorException;
import co.com.nequi.model.exception.NotFoundException;
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
                .doOnSubscribe(franchise -> System.out.println("Creating franchise"))
                .flatMap(franchiseUseCase::saveFranchise)
                .flatMap(franchise -> ServerResponse.ok().bodyValue(franchise))
                .onErrorResume(InternalServerErrorException.class, e -> ServerResponse.status(500).build());
    }

    public Mono<ServerResponse> getFranchiseById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return franchiseUseCase.getById(id)
                .doOnSubscribe(franchise -> System.out.println("Getting franchise by id"))
                .flatMap(franchise -> ServerResponse.ok().bodyValue(franchise))
                .onErrorResume(e -> {
                    if (e instanceof NotFoundException) {
                        return ServerResponse.notFound().build();
                    } else {
                        return ServerResponse.status(500).build(); // Default case for other exceptions
                    }
                });
    }

    public Mono<ServerResponse> getAllFranchises(ServerRequest request) {
        return franchiseUseCase.getAllFranchises()
                .collectList()
                .doOnSubscribe(franchises -> System.out.println("Getting all franchises"))
                .flatMap(franchises -> ServerResponse.ok().bodyValue(franchises))
                .onErrorResume(e -> {
                    if (e instanceof NotFoundException) {
                        return ServerResponse.notFound().build();
                    } else {
                        return ServerResponse.status(500).build(); // Default case for other exceptions
                    }
                });
    }

    public Mono<ServerResponse> updateFranchise(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(Franchise.class)
                .doOnSubscribe(franchise -> System.out.println("Updating franchise"))
                .flatMap(franchise -> {
                    franchise.setId(id);
                    return franchiseUseCase.updateFranchise(franchise);
                })
                .flatMap(franchise -> ServerResponse.ok().bodyValue(franchise))
                .onErrorResume(e -> {
                    if (e instanceof NotFoundException) {
                        return ServerResponse.notFound().build();
                    } else {
                        return ServerResponse.status(500).build(); // Default case for other exceptions
                    }
                });
    }

    public Mono<ServerResponse> updateNameFranchise(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(FranchiseRequest.class)
                .map(FranchiseRequest::getName)
                .doOnSubscribe(name -> System.out.println("Updating franchise name"))
                .flatMap(name -> franchiseUseCase.getById(id)
                        .flatMap(franchise -> {
                            franchise.setName(name);
                            return franchiseUseCase.updateFranchise(franchise);
                        }))
                .flatMap(franchise -> ServerResponse.ok().bodyValue(franchise))
                .onErrorResume(InternalServerErrorException.class, e -> ServerResponse.status(500).build());
    }
}
