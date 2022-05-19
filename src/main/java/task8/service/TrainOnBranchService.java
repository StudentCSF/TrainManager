package task8.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task8.component.InsertTrainIOnBranchValidationComponent;
import task8.component.TrainOnBranchValidationComponent;
import task8.exception.*;
import task8.model.entity.*;
import task8.model.input.InsertTrainOnBranchRequest;
import task8.model.input.TrainArrivedAtStationRequest;
import task8.repository.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TrainOnBranchService {

    private final TrainOnBranchValidationComponent trainOnBranchValidationComponent;

    private final TrainOnBranchRepository trainOnBranchRepository;

    private final StationOnBranchRepository stationOnBranchRepository;

    private final StationRepository stationRepository;

    private final BranchRepository branchRepository;

    private final TrainRepository trainRepository;

    private final InsertTrainIOnBranchValidationComponent insertTrainIOnBranchValidationComponent;

    private final CrashOnStationRepository crashOnStationRepository;


    @Autowired
    public TrainOnBranchService(TrainOnBranchValidationComponent trainOnBranchValidationComponent,
                                TrainOnBranchRepository trainOnBranchRepository,
                                StationOnBranchRepository stationOnBranchRepository,
                                StationRepository stationRepository,
                                BranchRepository branchRepository,
                                TrainRepository trainRepository,
                                InsertTrainIOnBranchValidationComponent insertTrainIOnBranchValidationComponent, CrashOnStationRepository crashOnStationRepository) {
        this.trainOnBranchValidationComponent = trainOnBranchValidationComponent;
        this.trainOnBranchRepository = trainOnBranchRepository;
        this.stationOnBranchRepository = stationOnBranchRepository;
        this.stationRepository = stationRepository;
        this.branchRepository = branchRepository;
        this.trainRepository = trainRepository;
        this.insertTrainIOnBranchValidationComponent = insertTrainIOnBranchValidationComponent;
        this.crashOnStationRepository = crashOnStationRepository;
    }

    public void insertTrainOnBranch(InsertTrainOnBranchRequest insertTrainOnBranchRequest) {
        if (!this.insertTrainIOnBranchValidationComponent.isValid(insertTrainIOnBranchValidationComponent)) {
            throw new RequestNotValidException();
        }
        BranchEntity branchEntity = this.branchRepository.findByName(insertTrainOnBranchRequest.getBranchName())
                .orElseThrow(BranchNotFoundException::new);
        StationEntity stationEntity = this.stationRepository.findByName(insertTrainOnBranchRequest.getStationName())
                .orElseThrow(StationNotFoundException::new);
        StationOnBranchEntity stationOnBranchEntity = this.stationOnBranchRepository.findByStUidAndBrUid(
                        stationEntity.getUid(),
                        branchEntity.getUid())
                .orElseThrow(StationOnBranchNotFoundException::new);
        this.trainOnBranchRepository.save(TrainOnBranchEntity.builder()
                .datetime(LocalDateTime.now())
                .forward(insertTrainOnBranchRequest.getForward() == null || insertTrainOnBranchRequest.getForward())
                .stOnBrUid(stationOnBranchEntity.getUid())
                .trUid(this.trainRepository.findByNumber(
                                insertTrainOnBranchRequest.getTrainNum())
                        .orElseThrow(TrainNotFoundException::new).getUid())
                .build());
    }

    public void processRequest(TrainArrivedAtStationRequest request) {
        if (!trainOnBranchValidationComponent.isValid(request)) {
            throw new RequestNotValidException();
        }

        TrainEntity trainEntity = this.trainRepository.findByNumber(request.getTrainNum())
                .orElseThrow(TrainNotFoundException::new);

        TrainOnBranchEntity trainOnBranchEntity = trainOnBranchRepository.findByTrUid(trainEntity.getUid())
                .orElseThrow(TrainOnBranchNotFoundException::new);

        StationOnBranchEntity prev = stationOnBranchRepository.findById(trainOnBranchEntity.getStOnBrUid())
                .orElseThrow(StationOnBranchNotFoundException::new);

        int where = trainOnBranchEntity.getForward() ? 1 : -1;

        StationOnBranchEntity curr = stationOnBranchRepository.findByBrUidAndPosition(prev.getBrUid(), prev.getPosition() + where)
                .orElse(null);

        if (curr == null) {
            where *= -1;
            trainOnBranchEntity.setForward(!trainOnBranchEntity.getForward());
            curr = stationOnBranchRepository.findByBrUidAndPosition(prev.getBrUid(), prev.getPosition() + where)
                    .orElseThrow(RuntimeException::new);
        }

        TrainOnBranchEntity newTrainOnBranch = this.trainOnBranchRepository.findByTrUid(trainEntity.getUid())
                        .orElseThrow(TrainNotFoundException::new);

//                TrainOnBranchEntity.builder()
                newTrainOnBranch.setDatetime(request.getDateTime());
                newTrainOnBranch.setForward(trainOnBranchEntity.getForward());
                newTrainOnBranch.setStOnBrUid(curr.getUid());
//                newTrainOnBranch.set.trUid(trainEntity.getUid())
//                .build();

        trainOnBranchRepository.save(newTrainOnBranch);

        String green = "\u001B[32m";
        String purple = "\u001B[35m";
        String blue = "\u001B[34m";
        String yellow = "\u001B[33m";
        String def = "\u001B[0m";

        System.out.printf("Поезд %s %s %s на ветке %s %s %s прибыл со станции %s %s %s на станцию %s %s %s в %s %s %s\n",
                yellow,
                request.getTrainNum(),
                def,
                purple,
                this.branchRepository.findById(curr.getBrUid())
                        .orElseThrow(NullPointerException::new).getName(),
                def,
                green,
                this.stationRepository.findById(prev.getStUid())
                        .orElseThrow(NullPointerException::new).getName(),
                def,
                green,
                this.stationRepository.findById(curr.getStUid())
                        .orElseThrow(NullPointerException::new).getName(),
                def,
                blue,
                request.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                def);
    }

    public boolean canMoving(Integer trainNum) {
        if (trainNum == null) {
            throw new RequestNotValidException();
        }
        return this.crashOnStationRepository.findByStUid(this.stationOnBranchRepository.findById(
                                this.trainOnBranchRepository.findByTrUid(
                                                this.trainRepository.findByNumber(trainNum)
                                                        .orElseThrow(TrainNotFoundException::new)
                                                        .getUid())
                                        .orElseThrow(TrainOnBranchNotFoundException::new)
                                        .getStOnBrUid())
                        .orElseThrow(StationOnBranchNotFoundException::new)
                        .getStUid())
                .isEmpty();
    }
}
