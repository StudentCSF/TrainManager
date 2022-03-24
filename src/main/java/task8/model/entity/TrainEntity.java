package task8.model.entity;

import lombok.Data;

import java.util.UUID;

@Data
public class TrainEntity {
    private UUID uid;
    private Boolean onRepair;
}
