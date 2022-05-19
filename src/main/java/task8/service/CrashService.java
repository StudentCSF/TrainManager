package task8.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task8.component.CrashRequestValidationComponent;
import task8.exception.CrashAlreadyExistsException;
import task8.exception.RequestNotValidException;
import task8.model.entity.CrashEntity;
import task8.model.input.CrashRequest;
import task8.repository.CrashRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CrashService {

    private final CrashRepository crashRepository;

    private final CrashRequestValidationComponent crashRequestValidationComponent;

    @Autowired
    public CrashService(CrashRepository crashRepository, CrashRequestValidationComponent crashRequestValidationComponent) {
        this.crashRepository = crashRepository;
        this.crashRequestValidationComponent = crashRequestValidationComponent;
    }

    public void addCrash(CrashRequest crashRequest) {
        if (!this.crashRequestValidationComponent.isValid(crashRequest)) {
            throw new RequestNotValidException();
        }

        if (this.crashRepository.findByName(crashRequest.getName()).isPresent()) {
            throw new CrashAlreadyExistsException();
        }

        crashRepository.save(CrashEntity.builder()
                .uid(UUID.randomUUID())
                .name(crashRequest.getName())
                .difficulty(crashRequest.getDifficulty())
                .build());
    }

    public List<String> getCrashNames() {
        return this.crashRepository.findAll().stream()
                .map(CrashEntity::getName)
                .collect(Collectors.toList());
    }
}
