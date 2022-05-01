package task8.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Entity
public class CrashOnStationEntity {

    //@Id
    private UUID uid;

    private UUID stUid;

    private UUID crUid;

    private LocalDateTime dateTime;
}
