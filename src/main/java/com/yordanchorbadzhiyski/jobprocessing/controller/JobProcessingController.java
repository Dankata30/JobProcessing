package com.yordanchorbadzhiyski.jobprocessing.controller;

import com.yordanchorbadzhiyski.jobprocessing.exceptions.CycleDetectedException;
import com.yordanchorbadzhiyski.jobprocessing.model.Task;
import com.yordanchorbadzhiyski.jobprocessing.model.TaskHandler;
import com.yordanchorbadzhiyski.jobprocessing.model.TasksResp;
import com.yordanchorbadzhiyski.jobprocessing.service.JobProcessingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling JSON payloads containing a list of tasks.
 * This class processes the tasks by building a Directed Acyclic Graph (DAG) based on the task dependencies,
 * performs a topological sort to determine the execution order, and returns the result as either a list of tasks
 * or a list of commands, depending on the specified 'type' in the request header.
 *
 * The class is mapped to the "/process" endpoint, and it exposes a POST method to handle incoming JSON payloads.
 * The processing involves building the DAG, checking for cycles in the graph, and performing a topological sort.
 *
 * @author Yordan Chorbadzhiyski
 * @version 1.0
 * @since 2024-01-23
 */
@RestController
@RequestMapping("/process")
public class JobProcessingController {

    private final JobProcessingService jobProcessingService;

    /**
     * Constructs a new JobProcessingController with the provided JobProcessingService.
     *
     * @param jobProcessingService The service responsible for processing the tasks.
     */
    public JobProcessingController(JobProcessingService jobProcessingService) {
        this.jobProcessingService = jobProcessingService;
    }

    /**
     * Process JSON payload containing a list of tasks, build a Directed Acyclic Graph (DAG)
     * based on the tasks, and perform a topological sort to determine the order in which
     * the tasks should be executed. The result is returned either as a list of tasks or a list
     * of commands depending on the specified 'type' in the request header.
     *
     * @param type   Optional request header specifying the desired response type ('bash' for commands).
     * @param tasks  Request body containing a list of tasks to be processed.
     * @return ResponseEntity containing either a list of tasks or a list of commands based on the 'type'.
     * @throws CycleDetectedException If a cycle is detected in the task dependencies, indicating an invalid DAG.
     */
    @PostMapping("")
    @ResponseBody
    public ResponseEntity processJson(@RequestHeader(required = false) String type, @RequestBody TaskHandler tasks) throws CycleDetectedException {
        jobProcessingService.buildDAG(tasks.getTasks());

        if(jobProcessingService.hasCycle()){
            throw new CycleDetectedException("The list of tasks cannot be performed in any order due to circular dependency");
        }

        List<Task> sortedTasks = jobProcessingService.topologicalSort();

        TasksResp resp = new TasksResp(sortedTasks);

        if(type == null || !type.equals("bash")){
            return new ResponseEntity(resp.getTasks(), HttpStatus.OK);
        }else{
            return new ResponseEntity(resp.getCommands() , HttpStatus.OK);
        }
    }
}
