package task8.model.entity;

import lombok.Data;

import java.util.UUID;

@Data
public class BranchEntity {
    private UUID uid;
    private String name;
}
