package com.example.demo.api;

import com.example.demo.domain.Trainee;
import com.example.demo.service.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/trainees")
public class TraineeController {
    private TraineeService traineeService;

    @Autowired
    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @PostMapping
    public Trainee addTrainee(@RequestBody @Valid Trainee trainee) {
        return traineeService.addTrainee(trainee);
    }

    @GetMapping
    public List<Trainee> findAllNonGroupTrainee(@PathParam("grouped") boolean grouped) {
        return traineeService.findTraineeByCondition(grouped);
    }
}
