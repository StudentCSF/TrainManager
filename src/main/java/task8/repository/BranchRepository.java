package task8.repository;

import org.springframework.stereotype.Repository;
import task8.model.entity.BranchEntity;

import java.util.*;

@Repository
public class BranchRepository {

    private final Map<UUID, BranchEntity> branches = new HashMap<>();

    public void insert(BranchEntity branch) {
        this.branches.put(branch.getUid(), branch);
    }

    public int update(BranchEntity branch) {
        if (this.branches.containsKey(branch.getUid())) {
            this.branches.put(branch.getUid(), branch);
            return 1;
        }
        return 0;
    }

    public BranchEntity delete(UUID uid) {
        return this.branches.remove(uid);
    }

    public Optional<BranchEntity> findById(UUID uid) {
        return Optional.ofNullable(this.branches.get(uid));
    }

    public List<BranchEntity> findAll() {
        return new ArrayList<>(this.branches.values());
    }

    public Optional<BranchEntity> findByName(String name) {
        return this.branches.values().stream()
                .filter(x -> name.equals(x.getName()))
                .findFirst();
    }
}
