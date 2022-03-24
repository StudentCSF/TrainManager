package task8.model.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TrainOnBranchEntity {
    private UUID uid;
    private UUID trUID;
    private UUID stOnBrUID;
    private LocalDateTime datetime;
    private Boolean forward;
}
