package task8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task8.model.entity.TrainEntity;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public interface TrainRepository extends JpaRepository<TrainEntity, UUID> {

    Optional<TrainEntity> findByNumber(Integer num);

//    private final Map<UUID, TrainEntity> trains = new HashMap<>();
//
//    public void insert(TrainEntity train) {
//        this.trains.put(train.getUid(), train);
//    }
//
//    public int update(TrainEntity train) {
//        if (this.trains.containsKey(train.getUid())) {
//            this.trains.put(train.getUid(), train);
//            return 1;
//        }
//        return 0;
//    }
//
//    public TrainEntity delete(UUID uid) {
//        return this.trains.remove(uid);
//    }
//
//    public List<TrainEntity> findAll() {
//        return new ArrayList<>(this.trains.values());
//    }
//
//    public Optional<TrainEntity> findById(UUID uid) {
//        return Optional.ofNullable(this.trains.get(uid));
//    }
//
//    public Optional<TrainEntity> findByNumber(Integer num) {
//        return this.trains.values().stream()
//                .filter(x -> num.equals(x.getNumber()))
//                .findFirst();
//    }
}
