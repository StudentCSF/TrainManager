package task8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import task8.model.input.CrashOnStationRequest;
import task8.service.CrashOnStationService;

@RestController
public class CrashOnStationController {

    private final CrashOnStationService crashOnStationService;

    @Autowired
    public CrashOnStationController(CrashOnStationService crashOnStationService) {
        this.crashOnStationService = crashOnStationService;
    }

    @PostMapping("/cos")
    public void addCrashOnStation(@RequestBody CrashOnStationRequest request) {
        this.crashOnStationService.processRequest(request);
    }

    @DeleteMapping("/cos/{station}")
    public void repair(@PathVariable(name = "station") String station) {
        this.crashOnStationService.repair(station);
    }
}
