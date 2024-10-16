package co.com.nequi.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("franchises")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FranchiseEntity {
    @Id
    private Long id;
    private String name;
}
