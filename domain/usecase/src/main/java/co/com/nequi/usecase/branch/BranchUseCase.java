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
                .onErrorMap(e -> new InternalServerErrorException());
    }

    public Mono<Branch> saveBranch(Branch branch) {
        return branchRepository.saveBranch(branch)
                .onErrorMap(e -> new InternalServerErrorException());
    }

    public Flux<Branch> getBranchesByFranchiseId(Long franchiseId) {
        return branchRepository.getBranchesByFranchiseId(franchiseId)
                .switchIfEmpty(Mono.error(new NotFoundException()))
                .onErrorMap(e -> new InternalServerErrorException());
    }

    public Mono<Branch> getById(Long id) {
        return branchRepository.getById(id)
                .switchIfEmpty(Mono.error(new NotFoundException()))
                .onErrorMap(e -> new InternalServerErrorException());
    }

    public Mono<Branch> updateBranch(Branch branch) {
        return branchRepository.updateBranch(branch)
                .onErrorMap(e -> new InternalServerErrorException());
    }
}
