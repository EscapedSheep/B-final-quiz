package com.example.demo.api;

import com.example.demo.domain.Trainer;
import com.example.demo.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/trainers")
public class TrainerController {
    private TrainerService trainerService;

    @Autowired
    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Trainer addTrainer(@RequestBody @Valid Trainer trainer) {
        return trainerService.addTrainer(trainer);
    }

    @GetMapping
    public List<Trainer> findTrainerByCondition(@PathParam("grouped") Boolean grouped) {
        return trainerService.findTrainerByCondition(grouped);
    }
}
