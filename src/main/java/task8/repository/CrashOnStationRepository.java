package task8.repository;

import task8.model.entity.CrashOnStationEntity;

import java.util.*;
import java.util.stream.Collectors;

public class CrashOnStationRepository {

    private final Map<UUID, CrashOnStationEntity> crashOnStationEntityMap = new HashMap<>();

    public Optional<CrashOnStationEntity> findById(UUID uid) {
        return Optional.of(this.crashOnStationEntityMap.get(uid));
    }

    public List<CrashOnStationEntity> findAll() {
        return new ArrayList<>(this.crashOnStationEntityMap.values());
    }

    public List<CrashOnStationEntity> findAllByCrashId(UUID crashUID) {
        return this.crashOnStationEntityMap.values().stream()
                .filter(x -> crashUID.equals(x.getCrUID()))
                .collect(Collectors.toList());
    }

    public Optional<CrashOnStationEntity> findByStationId(UUID stationUID) {
        return this.crashOnStationEntityMap.values().stream()
                .filter(x -> stationUID.equals(x.getStUID()))
                .findFirst();
    }
}
