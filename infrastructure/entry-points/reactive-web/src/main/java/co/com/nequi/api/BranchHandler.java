package co.com.nequi.api;

import co.com.nequi.api.dto.BranchRequest;
import co.com.nequi.model.branch.Branch;
import co.com.nequi.model.exception.InternalServerErrorException;
import co.com.nequi.model.exception.NotFoundException;
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
                .doOnSubscribe(branch -> System.out.println("Creating branch"))
                .flatMap(branchUseCase::saveBranch)
                .flatMap(branch -> ServerResponse.ok().bodyValue(branch))
                .onErrorResume(InternalServerErrorException.class, e -> ServerResponse.status(500).build());
    }

    public Mono<ServerResponse> getBranchById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return branchUseCase.getById(id)
                .doOnSubscribe(branch -> System.out.println("Getting branch by id"))
                .flatMap(branch -> ServerResponse.ok().bodyValue(branch))
                .onErrorResume(e -> {
                    if (e instanceof NotFoundException) {
                        return ServerResponse.notFound().build();
                    } else {
                        return ServerResponse.status(500).build(); // Default case for other exceptions
                    }
                });
    }

    public Mono<ServerResponse> getAllBranches(ServerRequest request) {
        return branchUseCase.getAllBranches()
                .collectList()
                .doOnSubscribe(branches -> System.out.println("Getting all branches"))
                .flatMap(branches -> ServerResponse.ok().bodyValue(branches))
                .onErrorResume(e -> {
                    if (e instanceof NotFoundException) {
                        return ServerResponse.notFound().build();
                    } else {
                        return ServerResponse.status(500).build(); // Default case for other exceptions
                    }
                });
    }

    public Mono<ServerResponse> updateBranch(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(Branch.class)
                .doOnSubscribe(branch -> System.out.println("Updating branch"))
                .flatMap(branch -> {
                    branch.setId(id);
                    return branchUseCase.updateBranch(branch);
                })
                .flatMap(branch -> ServerResponse.ok().bodyValue(branch))
                .onErrorResume(InternalServerErrorException.class, e -> ServerResponse.status(500).build());
    }

    public Mono<ServerResponse> updateNameBranch(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(BranchRequest.class)
                .map(BranchRequest::getName)
                .doOnSubscribe(name -> System.out.println("Updating branch name"))
                .flatMap(name -> branchUseCase.getById(id)
                        .flatMap(branch -> {
                            branch.setName(name);
                            return branchUseCase.updateBranch(branch);
                        }))
                .flatMap(branch -> ServerResponse.ok().bodyValue(branch))
                .onErrorResume(InternalServerErrorException.class, e -> ServerResponse.status(500).build());
    }
}
