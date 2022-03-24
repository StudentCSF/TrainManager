package task8.repository;

import task8.model.entity.StationOnBranchEntity;

import java.util.*;
import java.util.stream.Collectors;

public class StationOnBranchRepository {

    private final Map<UUID, StationOnBranchEntity> stationOnBranchEntityMap = new HashMap<>();

    public Optional<StationOnBranchEntity> findById(UUID uid) {
        return Optional.of(this.stationOnBranchEntityMap.get(uid));
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
