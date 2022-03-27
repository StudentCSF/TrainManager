package task8.service;

import task8.component.TrainOnBranchValidationComponent;
import task8.exception.RequestNotValidException;
import task8.exception.StationOnBranchNotFoundException;
import task8.exception.TrainNotFoundException;
import task8.model.entity.StationOnBranchEntity;
import task8.model.entity.TrainOnBranchEntity;
import task8.model.input.TrainArrivedAtStationRequest;
import task8.repository.StationOnBranchRepository;
import task8.repository.TrainOnBranchRepository;

public class TrainArrivedAtStationService {

    private final TrainOnBranchValidationComponent trainOnBranchValidationComponent = new TrainOnBranchValidationComponent();

    private final TrainOnBranchRepository trainOnBranchRepository = new TrainOnBranchRepository();

    private final StationOnBranchRepository stationOnBranchRepository = new StationOnBranchRepository();

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

        TrainOnBranchEntity newTrainOnBranch = new TrainOnBranchEntity();
        newTrainOnBranch.setDatetime(request.getDateTime());
        newTrainOnBranch.setForward(trainOnBranchEntity.getForward());
        newTrainOnBranch.setTrUid(request.getTrainUid());
        newTrainOnBranch.setStOnBrUid(curr.getUid());

        trainOnBranchRepository.update(newTrainOnBranch);
    }
}
