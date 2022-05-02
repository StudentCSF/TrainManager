package task8.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import task8.model.input.CrashOnStationRequest;
import task8.repository.CrashRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class CrashOnStationValidationComponent {

    private final Validator validator;

    private final CrashRepository crashRepository;

    @Autowired
    public CrashOnStationValidationComponent(Validator validator, CrashRepository crashRepository) {
        this.validator = validator;
        this.crashRepository = crashRepository;
    }

    public boolean isValid(CrashOnStationRequest crashOnStationRequest) {
        Set<ConstraintViolation<CrashOnStationRequest>> errors = validator.validate(crashOnStationRequest);
        boolean crashExist = crashRepository.findByName(crashOnStationRequest.getCrashName()).isPresent();
        return errors.isEmpty() && crashExist;
    }
}
