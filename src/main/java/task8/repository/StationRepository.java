package task8.repository;

import task8.model.entity.BranchEntity;
import task8.model.entity.StationEntity;

import java.util.*;

public class StationRepository {

    private final Map<UUID, StationEntity> stations = new HashMap<>();

    public void insert(StationEntity station) {
        this.stations.put(station.getUid(), station);
    }

    public int update(StationEntity station) {
        if (this.stations.containsKey(station.getUid())) {
            this.stations.put(station.getUid(), station);
            return 1;
        }
        return 0;
    }

    public StationEntity delete(UUID uid) {
        return this.stations.remove(uid);
    }

    public Optional<StationEntity> findById(UUID uid) {
        return Optional.of(this.stations.get(uid));
    }

    public List<StationEntity> findAll() {
        return new ArrayList<>(this.stations.values());
    }

    public Optional<StationEntity> findByName(String name) {
        return this.stations.values().stream().filter(x -> name.equals(x.getName())).findFirst();
    }
}
