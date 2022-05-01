package task8.component;

import org.springframework.stereotype.Component;
import task8.model.input.CrashOnStationRequest;
import task8.repository.CrashRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Component
public class CrashOnStationValidationComponent {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    private final Validator validator = factory.getValidator();

    private final CrashRepository crashRepository = new CrashRepository();

    public boolean isValid(CrashOnStationRequest crashOnStationRequest) {
        Set<ConstraintViolation<CrashOnStationRequest>> errors = validator.validate(crashOnStationRequest);
        boolean crashExist = crashRepository.findById(crashOnStationRequest.getCrashUid()).isPresent();
        return errors.isEmpty() && crashExist;
    }
}
