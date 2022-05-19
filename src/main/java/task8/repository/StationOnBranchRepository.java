package task8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task8.model.entity.StationOnBranchEntity;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public interface StationOnBranchRepository extends JpaRepository<StationOnBranchEntity, UUID> {

    List<StationOnBranchEntity> findAllByStUid(UUID uid);

    List<StationOnBranchEntity> findAllByBrUid(UUID uid);

    Optional<StationOnBranchEntity> findByStUidAndBrUid(UUID sUid, UUID bUid);

    Optional<StationOnBranchEntity> findByBrUidAndPosition(UUID uid, Integer pos);

//    private final Map<UUID, StationOnBranchEntity> stationOnBranchEntityMap = new HashMap<>();
//
//    public void insert(StationOnBranchEntity stationOnBranchEntity) {
//        this.stationOnBranchEntityMap.put(stationOnBranchEntity.getUid(), stationOnBranchEntity);
//    }
//
//    public int update(StationOnBranchEntity stationOnBranchEntity) {
//        if (this.stationOnBranchEntityMap.containsKey(stationOnBranchEntity.getUid())) {
//            this.stationOnBranchEntityMap.put(stationOnBranchEntity.getUid(), stationOnBranchEntity);
//            return 1;
//        }
//        return 0;
//    }
//
//    public StationOnBranchEntity delete(UUID stationOnBranchUID) {
//        return this.stationOnBranchEntityMap.remove(stationOnBranchUID);
//    }
//
//    public Optional<StationOnBranchEntity> findById(UUID uid) {
//        return Optional.ofNullable(this.stationOnBranchEntityMap.get(uid));
//    }
//
//    public List<StationOnBranchEntity> findAll() {
//        return new ArrayList<>(this.stationOnBranchEntityMap.values());
//    }
//
//    public List<StationOnBranchEntity> findAllByStationId(UUID stationUID) {
//        return this.stationOnBranchEntityMap.values().stream()
//                .filter(x -> stationUID.equals(x.getStUid()))
//                .collect(Collectors.toList());
//    }
//
//    public List<StationOnBranchEntity> findAllByBranchId(UUID branchUID) {
//        return this.stationOnBranchEntityMap.values().stream()
//                .filter(x -> branchUID.equals(x.getBrUid()))
//                .collect(Collectors.toList());
//    }
//
//    public Optional<StationOnBranchEntity> findByStationIdAndBranchId(UUID stationUid, UUID branchUid) {
//        return this.stationOnBranchEntityMap.values().stream()
//                .filter(x -> stationUid.equals(x.getStUid()) && branchUid.equals(x.getBrUid()))
//                .findFirst();
//    }
//
//    public Optional<StationOnBranchEntity> findByBranchUidAndPosition(UUID branchUid, int position) {
//        return this.stationOnBranchEntityMap.values().stream()
//                .filter(x -> branchUid.equals(x.getBrUid()) && position == x.getPosition())
//                .findFirst();
//    }
}
