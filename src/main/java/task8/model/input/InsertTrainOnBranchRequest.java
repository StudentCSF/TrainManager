package task8.model.input;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class InsertTrainOnBranchRequest {

    @NotNull
    @Min(0)
    private Integer trainNum;

    @NotBlank
    private String stationName;

    @NotBlank
    private String branchName;

    private Boolean forward;
}
