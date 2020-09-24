package com.example.demo.service;

import com.example.demo.domain.Trainee;
import com.example.demo.exception.IdNotExistedException;
import com.example.demo.repository.TraineeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraineeServiceTest {
    @Mock
    private TraineeRepository traineeRepository;

    @InjectMocks
    private TraineeService traineeService;

    private Trainee trainee;

    @BeforeEach
    void setUp() {
        trainee = Trainee.builder()
                .email("tw@thoughtworks.com")
                .github("github")
                .name("name")
                .office("office")
                .zoomId("zoomId")
                .Id(1)
                .build();
    }

    @Nested
    class AddTrainee {
        @Test
        void should_add_trainee_given_trainee_entity() {
            when(traineeRepository.save(any(Trainee.class))).thenReturn(trainee);

            Trainee savedTrainee = traineeService.addTrainee(trainee);

            assertEquals(savedTrainee, trainee);
            verify(traineeRepository).save(trainee);
        }
    }

    @Nested
    class DeleteTrainee {
        @Test
        void should_delete_trainee_given_existed_id() {
            when(traineeRepository.findById(anyInt())).thenReturn(Optional.of(trainee));

            traineeService.deleteTrainee(1);

            verify(traineeRepository).delete(trainee);
        }

        @Test
        void should_receive_error_given_non_existed_id() {
            when(traineeRepository.findById(anyInt())).thenReturn(Optional.empty());

            assertThrows(IdNotExistedException.class, () -> traineeService.deleteTrainee(1));
        }
    }
}