package task8.component;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import task8.model.input.TrainArrivedAtStationRequest;

import java.util.Set;

public class TrainOnBranchValidationComponent {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    private final Validator validator = factory.getValidator();

    public boolean isValid(TrainArrivedAtStationRequest trainArrivedAtStationRequest) {
        Set<ConstraintViolation<TrainArrivedAtStationRequest>> errors = validator.validate(trainArrivedAtStationRequest);
        return errors.isEmpty();
    }
}
