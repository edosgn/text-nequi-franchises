package co.com.nequi.r2dbc.helper;

import co.com.nequi.model.branch.Branch;
import co.com.nequi.r2dbc.entity.BranchEntity;


public interface BranchEntityMapper {
    BranchEntity toEntity(Branch branch);

    Branch toDomain(BranchEntity branchEntity);
}
