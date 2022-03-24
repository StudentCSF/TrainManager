package task8.repository;

import task8.model.entity.StationEntity;

import java.util.*;

public class StationRepository {

    private final Map<UUID, StationEntity> stations = new HashMap<>();

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
