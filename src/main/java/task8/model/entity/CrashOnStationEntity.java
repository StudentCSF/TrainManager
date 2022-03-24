package task8.model.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CrashOnStationEntity {
    private UUID uid;
    private UUID stUID;
    private UUID crUID;
    private LocalDateTime dateTime;
}
