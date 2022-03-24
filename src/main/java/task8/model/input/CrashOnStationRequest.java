package task8.model.input;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CrashOnStationRequest {

    @NotNull
    private UUID crashUid;

    @NotNull
    private String stationName;

    @NotNull
    private LocalDateTime dateTime;
}
