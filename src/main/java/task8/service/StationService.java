package task8.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task8.exception.RequestNotValidException;
import task8.exception.StationAlreadyExistsException;
import task8.model.entity.StationEntity;
import task8.repository.StationRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StationService {

    private final StationRepository stationRepository;

    @Autowired
    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public void addStation(String stationName) {
        if (stationName == null || stationName.length() == 0) {
            throw new RequestNotValidException();
        }
        if (this.stationRepository.findByName(stationName).isPresent()) {
            throw new StationAlreadyExistsException();
        }
        stationRepository.save(StationEntity.builder()
                .uid(UUID.randomUUID())
                .name(stationName)
                .build());
    }

    public List<String> getStationNames() {
        return this.stationRepository.findAll().stream()
                .map(StationEntity::getName)
                .collect(Collectors.toList());
    }
}
