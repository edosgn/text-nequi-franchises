package co.com.nequi.r2dbc.helper;


import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.r2dbc.entity.FranchiseEntity;
import org.springframework.stereotype.Component;

@Component
public class FranchiseEntityMapperImpl implements FranchiseEntityMapper {
    @Override
    public FranchiseEntity toEntity(Franchise franchise) {
        return FranchiseEntity.builder()
                .id(franchise.getId())
                .name(franchise.getName())
                .build();
    }

    @Override
    public Franchise toDomain(FranchiseEntity franchiseEntity) {
        return Franchise.builder()
                .id(franchiseEntity.getId())
                .name(franchiseEntity.getName())
                .build();
    }
}
