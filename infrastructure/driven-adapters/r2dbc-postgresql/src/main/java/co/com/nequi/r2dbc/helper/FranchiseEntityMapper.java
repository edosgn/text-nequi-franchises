package co.com.nequi.r2dbc.helper;

import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.r2dbc.entity.FranchiseEntity;

public interface FranchiseEntityMapper {
    FranchiseEntity toEntity(Franchise franchise);

    Franchise toDomain(FranchiseEntity franchiseEntity);
}
