package task8.repository;

import task8.model.entity.TrainEntity;

import java.util.*;
import java.util.stream.Collectors;

public class TrainRepository {

    private final Map<UUID, TrainEntity> trains = new HashMap<>();

    public void insert(TrainEntity train) {
        this.trains.put(train.getUid(), train);
    }

    public int update(TrainEntity train) {
        if (this.trains.containsKey(train.getUid())) {
            this.trains.put(train.getUid(), train);
            return 1;
        }
        return 0;
    }

    public TrainEntity delete(UUID uid) {
        return this.trains.remove(uid);
    }

    public List<TrainEntity> findAll() {
        return new ArrayList<>(this.trains.values());
    }

    public Optional<TrainEntity> findById(UUID uid) {
        return Optional.ofNullable(this.trains.get(uid));
    }

    public List<TrainEntity> findAllByOnRepair(Boolean onRepair) {
        return this.trains.values().stream()
                .filter(x -> onRepair.equals(x.getOnRepair()))
                .collect(Collectors.toList());
    }
}
