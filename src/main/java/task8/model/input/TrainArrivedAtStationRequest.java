package task8.model.input;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TrainArrivedAtStationRequest {

    @NotNull
    private UUID trainUid;

    @NotNull
    private LocalDateTime dateTime;
}
