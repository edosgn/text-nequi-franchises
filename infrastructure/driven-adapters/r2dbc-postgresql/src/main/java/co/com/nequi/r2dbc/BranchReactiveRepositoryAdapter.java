package co.com.nequi.r2dbc;

import co.com.nequi.model.branch.Branch;
import co.com.nequi.model.branch.gateways.BranchRepository;
import co.com.nequi.r2dbc.helper.BranchEntityMapperImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class BranchReactiveRepositoryAdapter implements BranchRepository {
    private final BranchReactiveRepository branchReactiveRepository;
    private final BranchEntityMapperImpl branchEntityMapper;

    @Override
    public Mono<Branch> saveBranch(Branch branch) {
        return branchReactiveRepository.save(branchEntityMapper.toEntity(branch))
                .map(branchEntityMapper::toDomain);
    }

    @Override
    public Mono<Branch> getById(Long id) {
        return branchReactiveRepository.findById(id)
                .map(branchEntityMapper::toDomain);
    }

    @Override
    public Flux<Branch> getAllBranches() {
        return branchReactiveRepository.findAll()
                .map(branchEntityMapper::toDomain);
    }

    @Override
    public Mono<Branch> updateBranch(Branch branch) {
        return branchReactiveRepository.save(branchEntityMapper.toEntity(branch))
                .map(branchEntityMapper::toDomain);
    }

    @Override
    public Flux<Branch> getBranchesByFranchiseId(Long franchiseId) {
        return branchReactiveRepository.getBranchesByFranchiseId(franchiseId)
                .map(branchEntityMapper::toDomain);
    }
}
