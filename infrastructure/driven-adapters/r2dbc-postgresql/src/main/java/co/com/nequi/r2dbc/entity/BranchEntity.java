package co.com.nequi.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("branches")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BranchEntity {
    @Id
    private Long id;
    private String name;
    private Long franchiseId;
}