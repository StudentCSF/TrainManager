package task8.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import task8.model.input.CrashOnStationRequest;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class CrashOnStationValidationComponent {

    private final Validator validator;

    @Autowired
    public CrashOnStationValidationComponent(Validator validator) {
        this.validator = validator;
    }

    public boolean isValid(CrashOnStationRequest crashOnStationRequest) {
        Set<ConstraintViolation<CrashOnStationRequest>> errors = validator.validate(crashOnStationRequest);
        return errors.isEmpty();
    }
}
