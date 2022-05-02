package task8.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task8.component.CrashOnStationValidationComponent;
import task8.exception.CrashNotFoundException;
import task8.exception.RequestNotValidException;
import task8.exception.StationNotFoundException;
import task8.model.entity.CrashOnStationEntity;
import task8.model.entity.StationEntity;
import task8.model.input.CrashOnStationRequest;
import task8.repository.CrashOnStationRepository;
import task8.repository.CrashRepository;
import task8.repository.StationRepository;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class CrashOnStationService {

    private final CrashOnStationValidationComponent validationComponent;

    private final StationRepository stationRepository;

    private final CrashOnStationRepository crashOnStationRepository;

    private final CrashRepository crashRepository;

    @Autowired
    public CrashOnStationService(CrashOnStationValidationComponent validationComponent,
                                 StationRepository stationRepository,
                                 CrashOnStationRepository crashOnStationRepository, CrashRepository crashRepository) {
        this.validationComponent = validationComponent;
        this.stationRepository = stationRepository;
        this.crashOnStationRepository = crashOnStationRepository;
        this.crashRepository = crashRepository;
    }

    public void processRequest(CrashOnStationRequest request) {
        if (!validationComponent.isValid(request)) {
            throw new RequestNotValidException();
        }

        StationEntity station = stationRepository.findByName(request.getStationName())
                .orElseThrow(StationNotFoundException::new);

        CrashOnStationEntity crashOnStationEntity = CrashOnStationEntity.builder()
                .crUid(this.crashRepository.findByName(request.getCrashName())
                        .orElseThrow(CrashNotFoundException::new)
                        .getUid())
                .uid(UUID.randomUUID())
                .dateTime(request.getDateTime())
                .stUid(station.getUid())
                .build();

        crashOnStationRepository.insert(crashOnStationEntity);

        System.out.printf("Произошел %s на станции %s в %s\n",
                request.getCrashName(),
                request.getStationName(),
                request.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        try {
            Thread.sleep(this.crashRepository.findById(crashOnStationEntity.getCrUid())
                    .orElseThrow(CrashNotFoundException::new).getDifficulty() * 100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.crashOnStationRepository.delete(crashOnStationEntity.getUid());

    }
}
