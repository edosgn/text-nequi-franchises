package co.com.nequi.api;

import co.com.nequi.model.branch.Branch;
import co.com.nequi.model.exception.InternalServerErrorException;
import co.com.nequi.usecase.branch.BranchUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BranchHandler {
    private final BranchUseCase branchUseCase;

    public Mono<ServerResponse> createBranch(ServerRequest request) {
        return request.bodyToMono(Branch.class)
                .flatMap(branchUseCase::saveBranch)
                .flatMap(branch -> ServerResponse.ok().bodyValue(branch))
                .onErrorResume(InternalServerErrorException.class, e -> ServerResponse.status(500).build());
    }

    public Mono<ServerResponse> getBranchById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return branchUseCase.getById(id)
                .flatMap(branch -> ServerResponse.ok().bodyValue(branch))
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(InternalServerErrorException.class, e -> ServerResponse.status(500).build());
    }

    public Mono<ServerResponse> getAllBranches(ServerRequest request) {
        return branchUseCase.getAllBranches()
                .collectList()
                .flatMap(branches -> ServerResponse.ok().bodyValue(branches))
                .onErrorResume(InternalServerErrorException.class, e -> ServerResponse.status(500).build());
    }

    public Mono<ServerResponse> updateBranch(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(Branch.class)
                .flatMap(branch -> {
                    branch.setId(id);
                    return branchUseCase.updateBranch(branch);
                })
                .flatMap(branch -> ServerResponse.ok().bodyValue(branch))
                .onErrorResume(InternalServerErrorException.class, e -> ServerResponse.status(500).build());
    }
}
