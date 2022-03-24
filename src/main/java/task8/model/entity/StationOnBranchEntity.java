package task8.model.entity;

import lombok.Data;

import java.util.UUID;

@Data
public class StationOnBranchEntity {
    private UUID uid;
    private UUID stUID;
    private UUID brUID;
    private Integer position;
}
