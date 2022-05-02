package task8.model.input;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
public class TrainArrivedAtStationRequest {

    @NotNull
    private Integer trainNum;

    @NotNull
    private LocalDateTime dateTime;
}
