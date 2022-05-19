package task8.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task8.component.CrashOnStationValidationComponent;
import task8.exception.*;
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
                                 CrashOnStationRepository crashOnStationRepository,
                                 CrashRepository crashRepository) {
        this.validationComponent = validationComponent;
        this.stationRepository = stationRepository;
        this.crashOnStationRepository = crashOnStationRepository;
        this.crashRepository = crashRepository;
    }

    public UUID processRequest(CrashOnStationRequest request) {
        if (!validationComponent.isValid(request)) {
            throw new RequestNotValidException();
        }

        if (crashOnStationRepository.findByStUid(
                        this.stationRepository.findByName(
                                        request.getStationName())
                                .orElseThrow(StationNotFoundException::new)
                                .getUid())
                .isPresent()
        ) {
            throw new CrashOnStationAlreadyExistsException();
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

        crashOnStationRepository.save(crashOnStationEntity);

        String red = "\u001B[31m";
        String blue = "\u001B[34m";
        String green = "\u001B[32m";
        String def = "\u001B[0m";
        System.out.printf("Произошел %s %s %s на станции %s %s %s в %s %s %s\n",
                red,
                request.getCrashName(),
                def,
                green,
                request.getStationName(),
                def,
                blue,
                request.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                def
        );

        return crashOnStationEntity.getUid();
    }

//    public void repair(UUID uid) {
//        this.crashOnStationRepository.delete(uid);
//    }

    public void repair(String stationName) {
        this.crashOnStationRepository.delete(
                this.crashOnStationRepository.findByStUid(
                                this.stationRepository.findByName(stationName)
                                        .orElseThrow(StationNotFoundException::new).getUid()
                        )
                        .orElseThrow(CrashNotFoundException::new)
        );
    }

    public Integer getCrashDifficulty(UUID crashOnStationUid) {
        return this.crashRepository.findById(this.crashOnStationRepository.findById(crashOnStationUid)
                        .orElseThrow(CrashOnStationNotFoundException::new)
                        .getCrUid())
                .orElseThrow(CrashNotFoundException::new)
                .getDifficulty();
    }
}
