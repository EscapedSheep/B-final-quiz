package com.example.demo.api;

import com.example.demo.domain.Trainee;
import com.example.demo.exception.IdNotExistedException;
import com.example.demo.service.TraineeService;
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

@WebMvcTest(TraineeController.class)
class TraineeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TraineeService traineeService;

    private ObjectMapper objectMapper;

    private Trainee trainee;

    @BeforeEach
    void setUp() {
        Mockito.reset(traineeService);
        objectMapper = new ObjectMapper();
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
        void should_add_trainee_given_trainee_info() throws Exception {
            when(traineeService.addTrainee(any(Trainee.class))).thenReturn(trainee);

            MockHttpServletResponse response = mockMvc.perform(post("/trainees").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(trainee)))
                    .andReturn().getResponse();

            assertEquals(response.getContentAsString(), objectMapper.writeValueAsString(trainee));
            assertEquals(response.getStatus(), HttpStatus.CREATED.value());
            verify(traineeService).addTrainee(trainee);
        }

        @Test
        void should_receive_error_given_invalid_trainee_info() throws Exception {
            trainee.setName("");

            MockHttpServletResponse response = mockMvc.perform(post("/trainees").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(trainee)))
                    .andReturn().getResponse();

            assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
        }
    }

    @Nested
    class GetTraineeByCondition {
        @Test
        void should_receive_trainee_non_grouped_given_false_path_param() throws Exception {
            when(traineeService.findTraineeByCondition(false)).thenReturn(Collections.singletonList(trainee));

            MockHttpServletResponse response = mockMvc.perform(get("/trainees").param("grouped", "false"))
                    .andReturn().getResponse();

            assertEquals(response.getStatus(), HttpStatus.OK.value());
            assertEquals(response.getContentAsString(), objectMapper.writeValueAsString(Collections.singletonList(trainee)));
            verify(traineeService).findTraineeByCondition(false);
        }

        @Test
        void should_receive_trainee_grouped_given_true_path_param() throws Exception {
            when(traineeService.findTraineeByCondition(true)).thenReturn(Collections.emptyList());

            MockHttpServletResponse response = mockMvc.perform(get("/trainees").param("grouped", "true"))
                    .andReturn().getResponse();

            assertEquals(response.getStatus(), HttpStatus.OK.value());
            assertEquals(response.getContentAsString(), objectMapper.writeValueAsString(Collections.emptyList()));
            verify(traineeService).findTraineeByCondition(true);
        }
    }

    @Nested
    class DeleteTrainee {
        @Test
        void should_delete_trainee_when_id_existed() throws Exception {
            MockHttpServletResponse response = mockMvc.perform(delete("/trainees/1"))
                    .andReturn().getResponse();

            assertEquals(response.getStatus(), HttpStatus.NO_CONTENT.value());
            verify(traineeService).deleteStudent(1);
        }

        @Test
        void should_receive_error_when_id_not_existed() throws Exception {
            doThrow(new IdNotExistedException()).when(traineeService).deleteStudent(anyInt());

            MockHttpServletResponse response = mockMvc.perform(delete("/trainees/1"))
                    .andReturn().getResponse();

            assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value());
            verify(traineeService).deleteStudent(1);
        }
    }
}