package task8.component;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import task8.model.input.AddStationToBranchRequest;
import task8.repository.CrashRepository;

import java.util.Set;

public class StationOnBranchValidationComponent {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    private final Validator validator = factory.getValidator();

    private final CrashRepository crashRepository = new CrashRepository();

    public boolean isValid(AddStationToBranchRequest addStationToBranchRequest) {
        Set<ConstraintViolation<AddStationToBranchRequest>> errors = validator.validate(addStationToBranchRequest);
        return errors.isEmpty();
    }
}
