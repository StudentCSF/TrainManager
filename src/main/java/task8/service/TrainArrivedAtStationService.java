package task8.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task8.component.TrainOnBranchValidationComponent;
import task8.exception.CrashNotFoundException;
import task8.exception.RequestNotValidException;
import task8.exception.StationOnBranchNotFoundException;
import task8.exception.TrainNotFoundException;
import task8.model.entity.CrashOnStationEntity;
import task8.model.entity.StationOnBranchEntity;
import task8.model.entity.TrainOnBranchEntity;
import task8.model.input.TrainArrivedAtStationRequest;
import task8.repository.*;

import java.time.format.DateTimeFormatter;

@Service
public class TrainArrivedAtStationService {

    private final TrainOnBranchValidationComponent trainOnBranchValidationComponent;

    private final TrainOnBranchRepository trainOnBranchRepository;

    private final StationOnBranchRepository stationOnBranchRepository;

    private final StationRepository stationRepository;

    private final BranchRepository branchRepository;

    private final TrainRepository trainRepository;

    private final CrashOnStationRepository crashOnStationRepository;

    private final CrashRepository crashRepository;


    @Autowired
    public TrainArrivedAtStationService(TrainOnBranchValidationComponent trainOnBranchValidationComponent,
                                        TrainOnBranchRepository trainOnBranchRepository,
                                        StationOnBranchRepository stationOnBranchRepository, StationRepository stationRepository, BranchRepository branchRepository, TrainRepository trainRepository, CrashOnStationRepository crashOnStationRepository, CrashRepository crashRepository) {
        this.trainOnBranchValidationComponent = trainOnBranchValidationComponent;
        this.trainOnBranchRepository = trainOnBranchRepository;
        this.stationOnBranchRepository = stationOnBranchRepository;
        this.stationRepository = stationRepository;
        this.branchRepository = branchRepository;
        this.trainRepository = trainRepository;
        this.crashOnStationRepository = crashOnStationRepository;
        this.crashRepository = crashRepository;
    }

    public void processRequest(TrainArrivedAtStationRequest request) {
        if (!trainOnBranchValidationComponent.isValid(request)) {
            throw new RequestNotValidException();
        }

        TrainOnBranchEntity trainOnBranchEntity = trainOnBranchRepository.findByTrainId(request.getTrainUid())
                .orElseThrow(TrainNotFoundException::new);

        StationOnBranchEntity prev = stationOnBranchRepository.findById(trainOnBranchEntity.getStOnBrUid())
                .orElseThrow(StationOnBranchNotFoundException::new);

        int where = trainOnBranchEntity.getForward() ? 1 : -1;

        StationOnBranchEntity curr = stationOnBranchRepository.findByBranchUidAndPosition(prev.getBrUid(), prev.getPosition() + where)
                .orElse(null);

        if (curr == null) {
            where *= -1;
            trainOnBranchEntity.setForward(!trainOnBranchEntity.getForward());
            curr = stationOnBranchRepository.findByBranchUidAndPosition(prev.getBrUid(), prev.getPosition() + where)
                    .orElseThrow(RuntimeException::new);
        }

        TrainOnBranchEntity newTrainOnBranch = TrainOnBranchEntity.builder()
                .datetime(request.getDateTime())
                .forward(trainOnBranchEntity.getForward())
                .stOnBrUid(curr.getUid())
                .trUid(request.getTrainUid())
                .build();

        trainOnBranchRepository.update(newTrainOnBranch);

        System.out.printf("Поезд %s на ветке %s прибыл со станции %s на станцию %s в %s\n",
                this.trainRepository.findById(request.getTrainUid())
                        .orElseThrow(NullPointerException::new).getNumber(),
                this.branchRepository.findById(curr.getBrUid())
                        .orElseThrow(NullPointerException::new).getName(),
                this.stationRepository.findById(prev.getStUid())
                        .orElseThrow(NullPointerException::new).getName(),
                this.stationRepository.findById(curr.getStUid())
                        .orElseThrow(NullPointerException::new).getName(),
                request.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }
}
