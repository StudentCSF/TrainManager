package task8.repository;

import task8.model.entity.CrashEntity;

import java.util.*;
import java.util.stream.Collectors;

public class CrashRepository {

    private final Map<UUID, CrashEntity> crashes = new HashMap<>();

    public List<CrashEntity> findAll() {
        return new ArrayList<>(this.crashes.values());
    }

    public Optional<CrashEntity> findById(UUID uid) {
        return Optional.of(crashes.get(uid));
    }

    public List<CrashEntity> findAllByDifficulty(Integer difficulty) {
        return this.crashes.values().stream()
                .filter(x -> difficulty.equals(x.getDifficulty()))
                .collect(Collectors.toList());
    }
}
