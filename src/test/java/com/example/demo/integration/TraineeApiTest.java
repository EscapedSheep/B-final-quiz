package com.example.demo.integration;

import com.example.demo.domain.Trainee;
import com.example.demo.repository.TraineeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
public class TraineeApiTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TraineeRepository traineeRepository;

    private ObjectMapper objectMapper;

    private Trainee trainee;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        trainee = Trainee.builder()
                .email("tw@thoughtworks.com")
                .github("github")
                .name("name")
                .office("office")
                .zoomId("zoomId")
                .build();
    }

    @Test
    void should_add_trainee_to_database_given_valid_trainee_info() throws Exception{
        mockMvc.perform(post("/trainees").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(trainee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(trainee.getName())))
                .andExpect(jsonPath("$.github",is(trainee.getGithub())))
                .andExpect(jsonPath("$.office", is(trainee.getOffice())))
                .andExpect(jsonPath("$.email", is(trainee.getEmail())))
                .andExpect(jsonPath("$.zoomId", is(trainee.getZoomId())));

        Trainee findTrainee = traineeRepository.findAll().get(0);

        assertEquals(findTrainee.getName(), trainee.getName());
    }
}
