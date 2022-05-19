package task8.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "station_on_branch")
public class StationOnBranchEntity {

    @Id
    private UUID uid;

    private UUID stUid;

    private UUID brUid;

    private Integer position;
}
