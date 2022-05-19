package task8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import task8.service.StationService;

import java.util.List;

@RestController
public class StationController {

    private final StationService stationService;

    @Autowired
    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @PostMapping("/station")
    public void addStation(@RequestBody String stationName) {
        this.stationService.addStation(stationName);
    }

    @GetMapping("/station")
    public List<String> getStaions() {
        return this.stationService.getStationNames();
    }
}
