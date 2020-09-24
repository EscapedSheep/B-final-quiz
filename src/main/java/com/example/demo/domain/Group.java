package com.example.demo.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static com.example.demo.constant.PropertyValidationMessage.NAME_NOT_BLANK;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "teamGroup")
public class Group {
    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = NAME_NOT_BLANK)
    private String name;

    @OneToMany(mappedBy = "teamGroup")
    private List<Trainer> trainers;

    @OneToMany(mappedBy = "teamGroup")
    private List<Trainee> trainees;

    public void addTrainee(Trainee trainee){
        this.trainees.add(trainee);
    }

    public void addTrainer(Trainer trainer) {
        this.trainers.add(trainer);
    }
}
