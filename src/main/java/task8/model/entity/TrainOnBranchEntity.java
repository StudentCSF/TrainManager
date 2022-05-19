package task8.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "train_on_branch")
public class TrainOnBranchEntity {

    @Id
    private UUID trUid;

    private UUID stOnBrUid;

    private LocalDateTime datetime;

    private Boolean forward;
}
