package task8.model.input;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddStationToBranchRequest {

    @NotNull
    private String stationName;

    @NotNull
    private String branchName;

    @NotNull
    @Min(1)
    private Integer position;
}
