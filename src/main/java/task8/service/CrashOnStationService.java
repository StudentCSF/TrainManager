package task8.service;

import task8.component.CrashOnStationValidationComponent;
import task8.exception.RequestNotValidException;
import task8.exception.StationNotFoundException;
import task8.model.entity.CrashOnStationEntity;
import task8.model.entity.StationEntity;
import task8.model.input.CrashOnStationRequest;
import task8.repository.CrashOnStationRepository;
import task8.repository.StationRepository;

import java.util.UUID;

public class CrashOnStationService {

    private final CrashOnStationValidationComponent validationComponent = new CrashOnStationValidationComponent();

    private final StationRepository stationRepository = new StationRepository();

    private final CrashOnStationRepository crashOnStationRepository = new CrashOnStationRepository();

    public void processRequest(CrashOnStationRequest request) {
        if (!validationComponent.isValid(request)) {
            throw new RequestNotValidException();
        }

        StationEntity station = stationRepository.findByName(request.getStationName())
                .orElseThrow(StationNotFoundException::new);

        CrashOnStationEntity crashOnStationEntity = new CrashOnStationEntity();
        crashOnStationEntity.setUid(UUID.randomUUID());
        crashOnStationEntity.setCrUID(request.getCrashUid());
        crashOnStationEntity.setDateTime(request.getDateTime());
        crashOnStationEntity.setStUID(station.getUid());

        crashOnStationRepository.insert(crashOnStationEntity);
    }
}
