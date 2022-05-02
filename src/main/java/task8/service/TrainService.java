package task8.service;

import org.springframework.stereotype.Service;
import task8.exception.RequestNotValidException;
import task8.exception.TrainAlreadyExistsException;
import task8.model.entity.TrainEntity;
import task8.repository.TrainRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TrainService {

    private final TrainRepository trainRepository;

    public TrainService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    public void addTrain(Integer num) {
        if (num == null) {
            throw new RequestNotValidException();
        }
        if (this.trainRepository.findByNumber(num).isPresent()) {
            throw new TrainAlreadyExistsException();
        }

        this.trainRepository.insert(TrainEntity.builder()
                .uid(UUID.randomUUID())
                .number(num)
                .build());
    }

    public List<Integer> getAllTrainNums() {
        return this.trainRepository.findAll().stream()
                .map(TrainEntity::getNumber)
                .collect(Collectors.toList());
    }
}
