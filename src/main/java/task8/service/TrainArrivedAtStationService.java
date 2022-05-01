package task8.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task8.component.TrainOnBranchValidationComponent;
import task8.exception.RequestNotValidException;
import task8.exception.StationOnBranchNotFoundException;
import task8.exception.TrainNotFoundException;
import task8.model.entity.StationOnBranchEntity;
import task8.model.entity.TrainOnBranchEntity;
import task8.model.input.TrainArrivedAtStationRequest;
import task8.repository.StationOnBranchRepository;
import task8.repository.TrainOnBranchRepository;

@Service
public class TrainArrivedAtStationService {

    private final TrainOnBranchValidationComponent trainOnBranchValidationComponent;

    private final TrainOnBranchRepository trainOnBranchRepository;

    private final StationOnBranchRepository stationOnBranchRepository;

    @Autowired
    public TrainArrivedAtStationService(TrainOnBranchValidationComponent trainOnBranchValidationComponent,
                                        TrainOnBranchRepository trainOnBranchRepository,
                                        StationOnBranchRepository stationOnBranchRepository) {
        this.trainOnBranchValidationComponent = trainOnBranchValidationComponent;
        this.trainOnBranchRepository = trainOnBranchRepository;
        this.stationOnBranchRepository = stationOnBranchRepository;
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

        StationOnBranchEntity curr = stationOnBranchRepository.findByBranchUidAndPosition(prev.getUid(), prev.getPosition() + where)
                .orElseThrow(RequestNotValidException::new);

        TrainOnBranchEntity newTrainOnBranch = TrainOnBranchEntity.builder()
                .datetime(request.getDateTime())
                .forward(trainOnBranchEntity.getForward())
                .stOnBrUid(curr.getUid())
                .trUid(request.getTrainUid())
                .build();

        trainOnBranchRepository.update(newTrainOnBranch);
    }
}
