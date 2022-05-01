package task8.repository;

import org.springframework.stereotype.Repository;
import task8.model.entity.TrainOnBranchEntity;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class TrainOnBranchRepository {

    Map<UUID, TrainOnBranchEntity> trainOnBranchEntityMap = new HashMap<>();

    public void insert(TrainOnBranchEntity trainOnBranchEntity) {
        this.trainOnBranchEntityMap.put(trainOnBranchEntity.getTrUid(), trainOnBranchEntity);
    }

    public int update(TrainOnBranchEntity trainOnBranchEntity) {
        if (this.trainOnBranchEntityMap.containsKey(trainOnBranchEntity.getTrUid())) {
            this.trainOnBranchEntityMap.put(trainOnBranchEntity.getTrUid(), trainOnBranchEntity);
            return 1;
        }
        return 0;
    }

    public TrainOnBranchEntity delete(UUID trainOnBranchUID) {
        return this.trainOnBranchEntityMap.remove(trainOnBranchUID);
    }

    public List<TrainOnBranchEntity> findAll() {
        return new ArrayList<>(this.trainOnBranchEntityMap.values());
    }

    public Optional<TrainOnBranchEntity> findByTrainId(UUID trainUID) {
        return Optional.ofNullable(this.trainOnBranchEntityMap.get(trainUID));
    }

    public List<TrainOnBranchEntity> findAllByStationOnBranchId(UUID stationUID) {
        return this.trainOnBranchEntityMap.values().stream()
                .filter(x -> stationUID.equals(x.getStOnBrUid()))
                .collect(Collectors.toList());
    }
}
