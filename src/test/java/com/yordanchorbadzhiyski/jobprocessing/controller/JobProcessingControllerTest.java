package com.yordanchorbadzhiyski.jobprocessing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yordanchorbadzhiyski.jobprocessing.model.Task;
import com.yordanchorbadzhiyski.jobprocessing.model.TaskHandler;
import com.yordanchorbadzhiyski.jobprocessing.model.TasksResp;
import com.yordanchorbadzhiyski.jobprocessing.service.JobProcessingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest

@AutoConfigureMockMvc
public class JobProcessingControllerTest {

    @MockBean
    private JobProcessingService service;

    @Autowired
    private MockMvc mockMvc;

    private List<Task> tasks;
    private TaskHandler requestObj;

    @BeforeEach
    void setUp() {
        // Initialize tasks and requestObj before each test
        tasks = new ArrayList<>(List.of(
                new Task(1, "task-1", "dummy1", new ArrayList<>()),
                new Task(2, "task-2", "dummy2", List.of(1)),
                new Task(3, "task-3", "dummy3", List.of(2))
        ));

        requestObj = new TaskHandler(tasks);
    }

    @Test
    public void testProcessJsonGetBashResponse() throws Exception{
        TasksResp response = new TasksResp(tasks);

        doNothing().when(service).buildDAG(any());
        doReturn(false).when(service).hasCycle();
        doReturn(requestObj.getTasks()).when(service).topologicalSort();

        mockMvc.perform(post("/process")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(requestObj))
                    .header("type", "bash"))
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(content().string(response.getCommands()));
    }

    @Test
    public void testProcessJsonGetJSONResponse() throws Exception{
        TasksResp response = new TasksResp(tasks);

        doNothing().when(service).buildDAG(any());
        doReturn(false).when(service).hasCycle();
        doReturn(requestObj.getTasks()).when(service).topologicalSort();

        // No header present
        mockMvc.perform(post("/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestObj)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(asJsonString(response.getTasks())));
    }

    @Test
    public void testProcessJsonGetHasCycleException() throws Exception {
        doNothing().when(service).buildDAG(any());
        doReturn(true).when(service).hasCycle();

        // Perform the POST request
        mockMvc.perform(post("/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestObj)))
                .andExpect(status().isBadRequest())  // Expect a 400 Bad Request status
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(content().string("DATA ERROR: The list of tasks cannot be performed in any order due to circular dependency"));
    }

    @Test
    public void testProcessJsonGetBadInputException() throws Exception {
        String badRequest = "definitely not JSON";

        doNothing().when(service).buildDAG(any());
        doReturn(false).when(service).hasCycle();

        // Perform the POST request
        mockMvc.perform(post("/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(badRequest)))
                .andExpect(status().isBadRequest())  // Expect a 400 Bad Request status
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(content().string("An error occurred: class org.springframework.http.converter.HttpMessageNotReadableException"));
    }


    static String asJsonString(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}
