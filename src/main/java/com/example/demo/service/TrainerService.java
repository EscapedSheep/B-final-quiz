package com.example.demo.service;

import com.example.demo.domain.Trainer;
import com.example.demo.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {
    public TrainerRepository trainerRepository;

    @Autowired
    public TrainerService(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    public Trainer addTrainer(Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    public List<Trainer> findTrainerByCondition(Boolean grouped) {
        return grouped ? trainerRepository.findAllByTeamGroupIdNotNull() : trainerRepository.findAllByTeamGroupId(null);
    }
}
