package com.example.demo.api;

import com.example.demo.domain.Trainee;
import com.example.demo.service.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/trainees")
public class TraineeController {
    // GET: - 构造器注入的属性推荐使用final
    private TraineeService traineeService;

    @Autowired
    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Trainee addTrainee(@RequestBody @Valid Trainee trainee) {
        return traineeService.addTrainee(trainee);
    }

    @GetMapping
    public List<Trainee> findTraineeByCondition(@PathParam("grouped") Boolean grouped) {
        return traineeService.findTraineeByCondition(grouped);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    // GTB: - @PathVariable应该显式写明value属性
    public void deleteStudent(@PathVariable Integer id) {
        traineeService.deleteTrainee(id);
    }
}
