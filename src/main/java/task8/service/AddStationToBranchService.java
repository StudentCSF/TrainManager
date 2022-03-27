package task8.service;

import task8.component.StationOnBranchValidationComponent;
import task8.exception.BranchNotFoundException;
import task8.exception.RequestNotValidException;
import task8.exception.StationNotFoundException;
import task8.model.entity.BranchEntity;
import task8.model.entity.StationEntity;
import task8.model.entity.StationOnBranchEntity;
import task8.model.input.AddStationToBranchRequest;
import task8.repository.BranchRepository;
import task8.repository.StationOnBranchRepository;
import task8.repository.StationRepository;

import java.util.UUID;

public class AddStationToBranchService {

    private final StationOnBranchValidationComponent stationOnBranchValidationComponent = new StationOnBranchValidationComponent();

    private final StationRepository stationRepository = new StationRepository();

    private final BranchRepository branchRepository = new BranchRepository();

    private final StationOnBranchRepository stationOnBranchRepository = new StationOnBranchRepository();

    public void processRequest(AddStationToBranchRequest request) {
        if (!stationOnBranchValidationComponent.isValid(request)) {
            throw new RequestNotValidException();
        }

        StationEntity station = stationRepository.findByName(request.getStationName())
                .orElseThrow(StationNotFoundException::new);
        BranchEntity branch = branchRepository.findByName(request.getBranchName())
                .orElseThrow(BranchNotFoundException::new);

        StationOnBranchEntity stationOnBranch = new StationOnBranchEntity();
        stationOnBranch.setUid(UUID.randomUUID());
        stationOnBranch.setBrUid(branch.getUid());
        stationOnBranch.setStUid(station.getUid());
        stationOnBranch.setPosition(request.getPosition());

        stationOnBranchRepository.insert(stationOnBranch);
    }
}
