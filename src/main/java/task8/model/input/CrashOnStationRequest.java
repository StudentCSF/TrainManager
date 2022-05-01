package task8.model.input;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CrashOnStationRequest {

    @NotNull
    private UUID crashUid;

    @NotBlank
    private String stationName;

    @NotNull
    private LocalDateTime dateTime;
}
