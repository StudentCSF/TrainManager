package task8.model.entity;

import lombok.Data;

import java.util.UUID;

@Data
public class CrashEntity {
    private UUID uid;
    private Integer difficulty;
}
