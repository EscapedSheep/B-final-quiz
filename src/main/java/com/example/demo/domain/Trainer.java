package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import static com.example.demo.constant.PropertyValidationMessage.NAME_NOT_BLANK;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Trainer {
    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = NAME_NOT_BLANK)
    private String name;

    @JsonIgnore
    @ManyToOne
    private Group teamGroup;
}
