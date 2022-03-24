package task8.repository;

import task8.model.entity.TrainOnBranchEntity;

import java.util.*;
import java.util.stream.Collectors;

public class TrainOnBranchRepository {

    Map<UUID, TrainOnBranchEntity> trainOnBranchEntityMap = new HashMap<>();

    public Optional<TrainOnBranchEntity> findById(UUID uid) {
        return Optional.of(this.trainOnBranchEntityMap.get(uid));
    }

    public List<TrainOnBranchEntity> findAll() {
        return new ArrayList<>(this.trainOnBranchEntityMap.values());
    }

    public List<TrainOnBranchEntity> findAllByTrainId(UUID trainUID) {
        return this.trainOnBranchEntityMap.values().stream()
                .filter(x -> trainUID.equals(x.getTrUID()))
                .collect(Collectors.toList());
    }

    public List<TrainOnBranchEntity> findAllByStationId(UUID stationUID) {
        return this.trainOnBranchEntityMap.values().stream()
                .filter(x -> stationUID.equals(x.getStUID()))
                .collect(Collectors.toList());
    }

    public List<TrainOnBranchEntity> findAllByBranchId(UUID branchUID) {
        return this.trainOnBranchEntityMap.values().stream()
                .filter(x -> branchUID.equals(x.getBrUID()))
                .collect(Collectors.toList());
    }
}
