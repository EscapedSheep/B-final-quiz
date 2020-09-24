package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static com.example.demo.constant.PropertyValidationMessage.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Trainee {
    @Id
    @GeneratedValue
    private int Id;

    @NotBlank(message = NAME_NOT_BLANK)
    private String name;

    @NotBlank(message = OFFICE_NOT_BLANK)
    private String office;

    @NotBlank(message = EMAIL_NOT_BLANK)
    @Email(message = EMAIL_NOT_VALID)
    private String email;

    @NotBlank(message = GITHUB_NOT_BLANK)
    private String github;

    @NotBlank(message = ZOOM_ID_NOT_BLANK)
    private String zoomId;

    @JsonIgnore
    @ManyToOne
    private Group teamGroup;
}
