package task8.repository;

import task8.model.entity.CrashOnStationEntity;

import java.util.*;
import java.util.stream.Collectors;

public class CrashOnStationRepository {

    private final Map<UUID, CrashOnStationEntity> crashOnStationEntityMap = new HashMap<>();

    public void insert(CrashOnStationEntity crashOnStationEntity) {
        this.crashOnStationEntityMap.put(crashOnStationEntity.getUid(), crashOnStationEntity);
    }

    public int update(CrashOnStationEntity crashOnStationEntity) {
        if (this.crashOnStationEntityMap.containsKey(crashOnStationEntity.getUid())) {
            this.crashOnStationEntityMap.put(crashOnStationEntity.getUid(), crashOnStationEntity);
            return 1;
        }
        return 0;
    }

    public CrashOnStationEntity delete(UUID crashOnStationUID) {
        return this.crashOnStationEntityMap.remove(crashOnStationUID);
    }

    public Optional<CrashOnStationEntity> findById(UUID uid) {
        return Optional.ofNullable(this.crashOnStationEntityMap.get(uid));
    }

    public List<CrashOnStationEntity> findAll() {
        return new ArrayList<>(this.crashOnStationEntityMap.values());
    }

    public List<CrashOnStationEntity> findAllByCrashId(UUID crashUID) {
        return this.crashOnStationEntityMap.values().stream()
                .filter(x -> crashUID.equals(x.getCrUid()))
                .collect(Collectors.toList());
    }

    public Optional<CrashOnStationEntity> findByStationId(UUID stationUID) {
        return this.crashOnStationEntityMap.values().stream()
                .filter(x -> stationUID.equals(x.getStUid()))
                .findFirst();
    }
}
