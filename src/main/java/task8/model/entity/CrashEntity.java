package task8.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Entity
public class CrashEntity {

    //@Id
    private UUID uid;

    private Integer difficulty;
}
