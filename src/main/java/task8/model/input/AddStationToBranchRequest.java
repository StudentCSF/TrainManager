package task8.model.input;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class AddStationToBranchRequest {

    @NotBlank
    private String stationName;

    @NotBlank
    private String branchName;

    @NotNull
    @Min(1)
    private Integer position;
}
