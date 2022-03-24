package task8.component;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import task8.model.input.CrashOnStationRequest;
import task8.repository.CrashRepository;

import java.util.Set;

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
