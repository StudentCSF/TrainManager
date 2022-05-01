package task8.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

@Service
public class AddStationToBranchService {

    private final StationOnBranchValidationComponent stationOnBranchValidationComponent;

    private final StationRepository stationRepository;

    private final BranchRepository branchRepository;

    private final StationOnBranchRepository stationOnBranchRepository;

    @Autowired
    public AddStationToBranchService(StationOnBranchValidationComponent stationOnBranchValidationComponent,
                                     StationRepository stationRepository,
                                     BranchRepository branchRepository,
                                     StationOnBranchRepository stationOnBranchRepository) {
        this.stationOnBranchValidationComponent = stationOnBranchValidationComponent;
        this.stationRepository = stationRepository;
        this.branchRepository = branchRepository;
        this.stationOnBranchRepository = stationOnBranchRepository;
    }

    public void processRequest(AddStationToBranchRequest request) {
        if (!stationOnBranchValidationComponent.isValid(request)) {
            throw new RequestNotValidException();
        }

        StationEntity station = stationRepository.findByName(request.getStationName())
                .orElseThrow(StationNotFoundException::new);

        BranchEntity branch = branchRepository.findByName(request.getBranchName())
                .orElseThrow(BranchNotFoundException::new);

        StationOnBranchEntity stationOnBranch = StationOnBranchEntity.builder()
                .brUid(branch.getUid())
                .position(request.getPosition())
                .stUid(station.getUid())
                .uid(UUID.randomUUID())
                .build();

        stationOnBranchRepository.insert(stationOnBranch);
    }
}
