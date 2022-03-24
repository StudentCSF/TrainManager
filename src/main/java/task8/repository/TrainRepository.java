package task8.repository;

import task8.model.entity.TrainEntity;

import java.util.*;
import java.util.stream.Collectors;

public class TrainRepository {

    private final Map<UUID, TrainEntity> trains = new HashMap<>();

    public List<TrainEntity> findAll() {
        return new ArrayList<>(this.trains.values());
    }

    public Optional<TrainEntity> findById(UUID uid) {
        return Optional.of(this.trains.get(uid));
    }

    public List<TrainEntity> findAllByOnRepair(Boolean onRepair) {
        return this.trains.values().stream()
                .filter(x -> onRepair.equals(x.getOnRepair()))
                .collect(Collectors.toList());
    }
}
