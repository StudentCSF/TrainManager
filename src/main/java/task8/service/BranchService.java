package task8.service;

import org.springframework.stereotype.Service;
import task8.exception.BranchAlreadyExistsException;
import task8.exception.RequestNotValidException;
import task8.model.entity.BranchEntity;
import task8.repository.BranchRepository;

import java.util.UUID;

@Service
public class BranchService {

    private final BranchRepository branchRepository;

    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public void addBranch(String branchName) {
        if (branchName == null || branchName.length() == 0) {
            throw new RequestNotValidException();
        }
        if (this.branchRepository.findByName(branchName).isPresent()) {
            throw new BranchAlreadyExistsException();
        }
        this.branchRepository.insert(BranchEntity.builder()
                .name(branchName)
                .uid(UUID.randomUUID())
                .build());
    }
}
