package task8.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import task8.model.input.CrashRequest;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class CrashRequestValidationComponent {

    private final Validator validator;

    @Autowired
    public CrashRequestValidationComponent(Validator validator) {
        this.validator = validator;
    }

    public boolean isValid(CrashRequest crashRequest) {
        Set<ConstraintViolation<CrashRequest>> errors = validator.validate(crashRequest);
        return errors.isEmpty();
    }
}
