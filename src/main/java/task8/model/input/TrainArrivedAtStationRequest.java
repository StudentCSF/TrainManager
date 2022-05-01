package task8.model.input;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TrainArrivedAtStationRequest {

    @NotNull
    private UUID trainUid;

    @NotNull
    private LocalDateTime dateTime;
}
