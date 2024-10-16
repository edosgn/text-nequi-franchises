package co.com.nequi.r2dbc.helper;

import co.com.nequi.model.branch.Branch;
import co.com.nequi.r2dbc.entity.BranchEntity;
import org.springframework.stereotype.Component;

@Component
public class BranchEntityMapperImpl implements BranchEntityMapper {
    @Override
    public BranchEntity toEntity(Branch branch) {
        return BranchEntity.builder()
                .id(branch.getId())
                .name(branch.getName())
                .franchiseId(branch.getFranchiseId())
                .build();
    }

    @Override
    public Branch toDomain(BranchEntity branchEntity) {
        return Branch.builder()
                .id(branchEntity.getId())
                .name(branchEntity.getName())
                .franchiseId(branchEntity.getFranchiseId())
                .build();
    }
}
