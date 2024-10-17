package co.com.nequi.usecase.branch;

import co.com.nequi.model.branch.Branch;
import co.com.nequi.model.branch.gateways.BranchRepository;
import co.com.nequi.model.exception.InternalServerErrorException;
import co.com.nequi.model.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BranchUseCase {
    public final BranchRepository branchRepository;

    public Flux<Branch> getAllBranches() {
        return branchRepository.getAllBranches()
                .switchIfEmpty(Mono.error(new NotFoundException()))
                .doOnError(e -> System.err.println("Error getting all branches: " + e.getMessage()))
                .onErrorMap(e -> {
                    if (e instanceof NotFoundException) {
                        return e;
                    }
                    return new InternalServerErrorException();
                });
    }

    public Mono<Branch> saveBranch(Branch branch) {
        return branchRepository.saveBranch(branch)
                .doOnError(e -> System.err.println("Error saving branch: " + e.getMessage()))
                .onErrorMap(e -> new InternalServerErrorException());
    }

    public Flux<Branch> getBranchesByFranchiseId(Long franchiseId) {
        return branchRepository.getBranchesByFranchiseId(franchiseId)
                .switchIfEmpty(Mono.error(new NotFoundException()))
                .doOnError(e -> System.err.println("Error getting branches by franchise id: " + e.getMessage()))
                .onErrorMap(e -> {
                    if (e instanceof NotFoundException) {
                        return e;
                    }
                    return new InternalServerErrorException();
                });
    }

    public Mono<Branch> getById(Long id) {
        return branchRepository.getById(id)
                .switchIfEmpty(Mono.error(new NotFoundException()))
                .doOnError(e -> System.err.println("Error getting branch by id: " + e.getMessage()))
                .onErrorMap(e -> {
                    if (e instanceof NotFoundException) {
                        return e;
                    }
                    return new InternalServerErrorException();
                });
    }

    public Mono<Branch> updateBranch(Branch branch) {
        return branchRepository.updateBranch(branch)
                .doOnError(e -> System.err.println("Error updating branch: " + e.getMessage()))
                .onErrorMap(e -> new InternalServerErrorException());
    }
}
