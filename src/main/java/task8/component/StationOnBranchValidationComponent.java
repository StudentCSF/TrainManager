package task8.component;

import org.springframework.stereotype.Component;
import task8.model.input.AddStationToBranchRequest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Component
public class StationOnBranchValidationComponent {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    private final Validator validator = factory.getValidator();

    public boolean isValid(AddStationToBranchRequest addStationToBranchRequest) {
        Set<ConstraintViolation<AddStationToBranchRequest>> errors = validator.validate(addStationToBranchRequest);
        return errors.isEmpty();
    }
}
