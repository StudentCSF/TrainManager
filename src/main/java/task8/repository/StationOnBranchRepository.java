package task8.repository;

import task8.model.entity.StationOnBranchEntity;

import java.util.*;
import java.util.stream.Collectors;

public class StationOnBranchRepository {

    private final Map<UUID, StationOnBranchEntity> stationOnBranchEntityMap = new HashMap<>();

    public void insert(StationOnBranchEntity stationOnBranchEntity) {
        this.stationOnBranchEntityMap.put(stationOnBranchEntity.getUid(), stationOnBranchEntity);
    }

    public int update(StationOnBranchEntity stationOnBranchEntity) {
        if (this.stationOnBranchEntityMap.containsKey(stationOnBranchEntity.getUid())) {
            this.stationOnBranchEntityMap.put(stationOnBranchEntity.getUid(), stationOnBranchEntity);
            return 1;
        }
        return 0;
    }

    public StationOnBranchEntity delete(UUID stationOnBranchUID) {
        return this.stationOnBranchEntityMap.remove(stationOnBranchUID);
    }

    public Optional<StationOnBranchEntity> findById(UUID uid) {
        return Optional.ofNullable(this.stationOnBranchEntityMap.get(uid));
    }

    public List<StationOnBranchEntity> findAll() {
        return new ArrayList<>(this.stationOnBranchEntityMap.values());
    }

    public List<StationOnBranchEntity> findAllByStationId(UUID stationUID) {
        return this.stationOnBranchEntityMap.values().stream()
                .filter(x -> stationUID.equals(x.getStUID()))
                .collect(Collectors.toList());
    }

    public List<StationOnBranchEntity> findAllByBranchId(UUID branchUID) {
        return this.stationOnBranchEntityMap.values().stream()
                .filter(x -> branchUID.equals(x.getBrUID()))
                .collect(Collectors.toList());
    }
}
