package task8.model.input;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class CrashRequest {

    @NotBlank
    private String name;

    @NotNull
    private Integer difficulty;
}
