package task8.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import task8.model.input.AddStationToBranchRequest;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class StationOnBranchValidationComponent {

    private final Validator validator;

    @Autowired
    public StationOnBranchValidationComponent(Validator validator) {
        this.validator = validator;
    }

    public boolean isValid(AddStationToBranchRequest addStationToBranchRequest) {
        Set<ConstraintViolation<AddStationToBranchRequest>> errors = this.validator.validate(addStationToBranchRequest);
        return errors.isEmpty();
    }
}
