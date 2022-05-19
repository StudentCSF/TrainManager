package task8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import task8.model.input.CrashRequest;
import task8.service.CrashService;

@RestController
public class CrashController {

    private final CrashService crashService;

    @Autowired
    public CrashController(CrashService crashService) {
        this.crashService = crashService;
    }

    @PostMapping("/crash")
    public void addCrash(@RequestBody CrashRequest crashRequest) {
        this.crashService.addCrash(crashRequest);
    }
}
