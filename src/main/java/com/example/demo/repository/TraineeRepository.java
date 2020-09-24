package com.example.demo.repository;

import com.example.demo.domain.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Integer> {
    public List<Trainee> findAllByTeamGroupId(Integer groupId);

    public List<Trainee> findAllByTeamGroupIdNotNull();
}
