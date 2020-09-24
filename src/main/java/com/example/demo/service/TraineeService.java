package com.example.demo.service;

import com.example.demo.domain.Trainee;
import com.example.demo.exception.IdNotExistedException;
import com.example.demo.repository.TraineeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeService {
    private TraineeRepository traineeRepository;

    @Autowired
    public TraineeService(TraineeRepository traineeRepository) {
        this.traineeRepository = traineeRepository;
    }

    public Trainee addTrainee(Trainee trainee) {
        return traineeRepository.save(trainee);
    }

    public List<Trainee> findTraineeByCondition(Boolean grouped) {
        return grouped ? traineeRepository.findAllByTeamGroupIdNotNull() :traineeRepository.findAllByTeamGroupId(null);
    }

    public void deleteTrainee(int id) {
        Trainee trainee = traineeRepository.findById(id).orElseThrow(IdNotExistedException::new);
        traineeRepository.delete(trainee);
    }
}
