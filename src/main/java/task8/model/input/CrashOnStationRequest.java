package task8.model.input;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
public class CrashOnStationRequest {

    @NotBlank
    private String crashName;

    @NotBlank
    private String stationName;

    @NotNull
    private LocalDateTime dateTime;
}
