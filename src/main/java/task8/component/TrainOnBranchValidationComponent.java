package task8.component;

import org.springframework.stereotype.Component;
import task8.model.input.TrainArrivedAtStationRequest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Component
public class TrainOnBranchValidationComponent {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    private final Validator validator = factory.getValidator();

    public boolean isValid(TrainArrivedAtStationRequest trainArrivedAtStationRequest) {
        Set<ConstraintViolation<TrainArrivedAtStationRequest>> errors = validator.validate(trainArrivedAtStationRequest);
        return errors.isEmpty();
    }
}
