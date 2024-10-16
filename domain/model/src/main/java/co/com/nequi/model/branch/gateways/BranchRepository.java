package co.com.nequi.model.branch.gateways;

import co.com.nequi.model.branch.Branch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchRepository {
    Flux<Branch> getAllBranches();
    Mono<Branch> saveBranch(Branch branch);
    Flux<Branch> getBranchesByFranchiseId(Long franchiseId);
    Mono<Branch> getById(Long id);
    Mono<Branch> updateBranch(Branch branch);
}
