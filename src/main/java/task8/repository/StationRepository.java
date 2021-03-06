package task8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task8.model.entity.StationEntity;

import java.util.*;

@Repository
public interface StationRepository extends JpaRepository<StationEntity, UUID> {

    Optional<StationEntity> findByName(String name);

//    private final Map<UUID, StationEntity> stations = new HashMap<>();
//
//    public void insert(StationEntity station) {
//        this.stations.put(station.getUid(), station);
//    }
//
//    public int update(StationEntity station) {
//        if (this.stations.containsKey(station.getUid())) {
//            this.stations.put(station.getUid(), station);
//            return 1;
//        }
//        return 0;
//    }
//
//    public StationEntity delete(UUID uid) {
//        return this.stations.remove(uid);
//    }
//
//    public Optional<StationEntity> findById(UUID uid) {
//        return Optional.ofNullable(this.stations.get(uid));
//    }
//
//    public List<StationEntity> findAll() {
//        return new ArrayList<>(this.stations.values());
//    }
//
//    public Optional<StationEntity> findByName(String name) {
//        return this.stations.values().stream().filter(x -> name.equals(x.getName())).findFirst();
//    }
}
