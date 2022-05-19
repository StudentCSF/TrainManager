package task8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import task8.model.input.AddStationToBranchRequest;
import task8.service.StationOnBranchService;

@RestController
public class StationOnBranchController {

    private final StationOnBranchService stationOnBranchService;

    @Autowired
    public StationOnBranchController(StationOnBranchService stationOnBranchService) {
        this.stationOnBranchService = stationOnBranchService;
    }

    @PostMapping("/sob")
    public void addStationOnBranch(@RequestBody AddStationToBranchRequest request) {
        this.stationOnBranchService.processRequest(request);
    }
}
