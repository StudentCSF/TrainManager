package task8.repository;

import task8.model.entity.BranchEntity;

import java.util.*;

public class BranchRepository {

    private final Map<UUID, BranchEntity> branches = new HashMap<>();

    public Optional<BranchEntity> findById(UUID uid) {
        return Optional.of(this.branches.get(uid));
    }

    public List<BranchEntity> findAll() {
        return new ArrayList<>(this.branches.values());
    }

    public Optional<BranchEntity> findByName(String name) {
        return this.branches.values().stream().filter(x -> name.equals(x.getName())).findFirst();
    }
}
