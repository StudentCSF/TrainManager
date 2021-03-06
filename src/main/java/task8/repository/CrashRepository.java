package task8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task8.model.entity.CrashEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CrashRepository extends JpaRepository<CrashEntity, UUID> {

    List<CrashEntity> findAllByDifficulty(Integer diff);

    Optional<CrashEntity> findByName(String name);

//    private final Map<UUID, CrashEntity> crashes = new HashMap<>();
//
//    public void insert(CrashEntity crash) {
//        this.crashes.put(crash.getUid(), crash);
//    }
//
//    public int update(CrashEntity crash) {
//        if (this.crashes.containsKey(crash.getUid())) {
//            this.crashes.put(crash.getUid(), crash);
//            return 1;
//        }
//        return 0;
//    }
//
//    public CrashEntity delete(UUID uid) {
//        return this.crashes.remove(uid);
//    }
//
//    public List<CrashEntity> findAll() {
//        return new ArrayList<>(this.crashes.values());
//    }
//
//    public Optional<CrashEntity> findById(UUID uid) {
//        return Optional.ofNullable(crashes.get(uid));
//    }
//
//    public List<CrashEntity> findAllByDifficulty(Integer difficulty) {
//        return this.crashes.values().stream()
//                .filter(x -> difficulty.equals(x.getDifficulty()))
//                .collect(Collectors.toList());
//    }
//
//    public Optional<CrashEntity> findByName(String name) {
//       return this.crashes.values().stream()
//                .filter(x -> x.getName().equals(name))
//                .findFirst();
//    }
}
