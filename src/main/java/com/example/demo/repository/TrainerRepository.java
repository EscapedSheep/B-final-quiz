package com.example.demo.repository;

import com.example.demo.domain.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Integer> {
    public List<Trainer> findAllByTeamGroupId(Integer id);

    public List<Trainer> findAllByTeamGroupIdNotNull();
}
