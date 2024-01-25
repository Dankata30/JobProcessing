package com.yordanchorbadzhiyski.jobprocessing.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yordanchorbadzhiyski.jobprocessing.model.Response;
import com.yordanchorbadzhiyski.jobprocessing.model.Task;
import com.yordanchorbadzhiyski.jobprocessing.model.TaskHandler;
import com.yordanchorbadzhiyski.jobprocessing.utils.DAG;
import com.yordanchorbadzhiyski.jobprocessing.utils.JSONParser;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/process")
public class JobProcessingController {

    @PostMapping("")
    public Response processJson(@RequestBody String request) throws JsonProcessingException {
        JSONParser parser = new JSONParser();
        List<Task> tasks = parser.ParseRequest(request).getTasks();

        DAG dag = new DAG();
        dag.buildDAG(tasks);
        List<Task> sortedTasks = dag.topologicalSort();

        for (Task task : sortedTasks) {
            System.out.println("Task Id:" + task.getId());
            System.out.println("Task Name: " + task.getName());
            System.out.println("Task Command: " + task.getCommand());
            System.out.println("Task Requires: " + task.getRequires());
            System.out.println();
        }

        return new Response("dummy");
    }


}
