package task8.model.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CrashOnStationEntity {
    private UUID uid;
    private UUID stUid;
    private UUID crUid;
    private LocalDateTime dateTime;
}
