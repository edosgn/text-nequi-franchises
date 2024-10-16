package co.com.nequi.model.franchise.dto;

import co.com.nequi.model.branch.Branch;
import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record FranchiseDto(Long id, String name, List<Branch> branchList) {
}
