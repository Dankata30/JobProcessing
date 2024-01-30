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

@RestController
@RequestMapping("/process")
public class JobProcessingController {

    private final JobProcessingService jobProcessingService;

    public JobProcessingController(JobProcessingService jobProcessingService) {
        this.jobProcessingService = jobProcessingService;
    }

    @PostMapping("")
    @ResponseBody
    public ResponseEntity processJson(@RequestHeader(required = false) String type, @RequestBody TaskHandler tasks) throws CycleDetectedException {
        jobProcessingService.buildDAG(tasks.getTasks());

        System.out.println("Lets see " + jobProcessingService.hasCycle());

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
