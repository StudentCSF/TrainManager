package task8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import task8.model.input.InsertTrainOnBranchRequest;
import task8.model.input.TrainArrivedAtStationRequest;
import task8.service.TrainOnBranchService;

@RestController
public class TrainOnBranchController {

    private final TrainOnBranchService trainOnBranchService;

    @Autowired
    public TrainOnBranchController(TrainOnBranchService trainOnBranchService) {
        this.trainOnBranchService = trainOnBranchService;
    }

    @PostMapping("/tob")
    public void insertTrain(@RequestBody InsertTrainOnBranchRequest request) {
        this.trainOnBranchService.insertTrainOnBranch(request);
    }

    @PutMapping("/tob")
    public void arriving(@RequestBody TrainArrivedAtStationRequest request) {
        this.trainOnBranchService.processRequest(request);
    }
}
