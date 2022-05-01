package task8.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task8.component.CrashOnStationValidationComponent;
import task8.exception.RequestNotValidException;
import task8.exception.StationNotFoundException;
import task8.model.entity.CrashOnStationEntity;
import task8.model.entity.StationEntity;
import task8.model.input.CrashOnStationRequest;
import task8.repository.CrashOnStationRepository;
import task8.repository.StationRepository;

import java.util.UUID;

@Service
public class CrashOnStationService {

    private final CrashOnStationValidationComponent validationComponent;

    private final StationRepository stationRepository;

    private final CrashOnStationRepository crashOnStationRepository;

    @Autowired
    public CrashOnStationService(CrashOnStationValidationComponent validationComponent,
                                 StationRepository stationRepository,
                                 CrashOnStationRepository crashOnStationRepository) {
        this.validationComponent = validationComponent;
        this.stationRepository = stationRepository;
        this.crashOnStationRepository = crashOnStationRepository;
    }

    public void processRequest(CrashOnStationRequest request) {
        if (!validationComponent.isValid(request)) {
            throw new RequestNotValidException();
        }

        StationEntity station = stationRepository.findByName(request.getStationName())
                .orElseThrow(StationNotFoundException::new);

        CrashOnStationEntity crashOnStationEntity = CrashOnStationEntity.builder()
                .crUid(request.getCrashUid())
                .uid(UUID.randomUUID())
                .dateTime(request.getDateTime())
                .stUid(station.getUid())
                .build();

        crashOnStationRepository.insert(crashOnStationEntity);
    }
}
