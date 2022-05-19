package task8.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import task8.model.input.TrainArrivedAtStationRequest;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class TrainOnBranchValidationComponent {

    private final Validator validator;

    @Autowired
    public TrainOnBranchValidationComponent(Validator validator) {
        this.validator = validator;
    }

    public boolean isValid(TrainArrivedAtStationRequest trainArrivedAtStationRequest) {
        Set<ConstraintViolation<TrainArrivedAtStationRequest>> errors = validator.validate(trainArrivedAtStationRequest);
        return errors.isEmpty();
    }
}
