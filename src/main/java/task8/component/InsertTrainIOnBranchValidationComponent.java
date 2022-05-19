package task8.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class InsertTrainIOnBranchValidationComponent {

    private final Validator validator;

    @Autowired
    public InsertTrainIOnBranchValidationComponent(Validator validator) {
        this.validator = validator;
    }

    public boolean isValid(InsertTrainIOnBranchValidationComponent i) {
        Set<ConstraintViolation<InsertTrainIOnBranchValidationComponent>> errors = validator.validate(i);
        return errors.isEmpty();
    }
}
