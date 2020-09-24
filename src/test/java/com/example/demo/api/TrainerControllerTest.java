package com.example.demo.api;

import com.example.demo.domain.Trainer;
import com.example.demo.exception.IdNotExistedException;
import com.example.demo.service.TrainerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@WebMvcTest(TrainerController.class)
class TrainerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainerService trainerService;

    private ObjectMapper objectMapper;

    private Trainer trainer;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        Mockito.reset(trainerService);
        trainer = Trainer.builder()
                .id(1)
                .name("name")
                .build();
    }

    @Nested
    class AddTrainer {
        @Test
        void should_add_trainer_given_valid_trainer_info() throws Exception{
            when(trainerService.addTrainer(any(Trainer.class))).thenReturn(trainer);

            MockHttpServletResponse response = mockMvc.perform(post("/trainers").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(trainer)))
                    .andReturn().getResponse();

            assertEquals(response.getContentAsString(), objectMapper.writeValueAsString(trainer));
            assertEquals(response.getStatus(), HttpStatus.CREATED.value());
            verify(trainerService).addTrainer(trainer);
        }

        @Test
        void should_receive_error_given_invalid_trainer_info() throws Exception{
            trainer.setName("");

            MockHttpServletResponse response = mockMvc.perform(post("/trainers").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(trainer)))
                    .andReturn().getResponse();

            assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
        }
    }

    @Nested
    class FindTrainerByCondition {
        @Test
        void should_receive_trainer_non_grouped_given_false_path_param() throws Exception {
            when(trainerService.findTrainerByCondition(false)).thenReturn(Collections.singletonList(trainer));

            MockHttpServletResponse response = mockMvc.perform(get("/trainers").param("grouped", "false"))
                    .andReturn().getResponse();

            assertEquals(response.getStatus(), HttpStatus.OK.value());
            assertEquals(response.getContentAsString(), objectMapper.writeValueAsString(Collections.singletonList(trainer)));
            verify(trainerService).findTrainerByCondition(false);
        }

        @Test
        void should_receive_trainer_grouped_given_true_path_param() throws Exception {
            when(trainerService.findTrainerByCondition(true)).thenReturn(Collections.emptyList());

            MockHttpServletResponse response = mockMvc.perform(get("/trainers").param("grouped", "true"))
                    .andReturn().getResponse();

            assertEquals(response.getStatus(), HttpStatus.OK.value());
            assertEquals(response.getContentAsString(), objectMapper.writeValueAsString(Collections.emptyList()));
            verify(trainerService).findTrainerByCondition(true);
        }
    }

    @Nested
    class DeleteTrainer {
        @Test
        void should_delete_trainee_when_id_existed() throws Exception {
            MockHttpServletResponse response = mockMvc.perform(delete("/trainers/1"))
                    .andReturn().getResponse();

            assertEquals(response.getStatus(), HttpStatus.NO_CONTENT.value());
            verify(trainerService).deleteTrainer(1);
        }

        @Test
        void should_receive_error_when_id_not_existed() throws Exception {
            doThrow(new IdNotExistedException()).when(trainerService).deleteTrainer(anyInt());

            MockHttpServletResponse response = mockMvc.perform(delete("/trainers/1"))
                    .andReturn().getResponse();

            assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value());
            verify(trainerService).deleteTrainer(1);
        }
    }
}