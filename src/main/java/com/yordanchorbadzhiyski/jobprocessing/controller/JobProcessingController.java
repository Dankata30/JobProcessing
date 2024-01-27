package com.yordanchorbadzhiyski.jobprocessing.controller;

import com.yordanchorbadzhiyski.jobprocessing.exceptions.CycleDetectedException;
import com.yordanchorbadzhiyski.jobprocessing.model.Task;
import com.yordanchorbadzhiyski.jobprocessing.model.TaskHandler;
import com.yordanchorbadzhiyski.jobprocessing.model.TasksResp;
import com.yordanchorbadzhiyski.jobprocessing.service.SortTasks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/process")
public class JobProcessingController {

    @PostMapping("")
    @ResponseBody
    public ResponseEntity processJson(@RequestParam String type, @RequestBody TaskHandler tasks) throws CycleDetectedException {
        SortTasks sortTasks = new SortTasks();
        sortTasks.buildDAG(tasks.getTasks());

        if(sortTasks.hasCycle()){
            throw new CycleDetectedException("The list of tasks cannot be performed in any order due to circular dependency");
        }

        List<Task> sortedTasks = sortTasks.topologicalSort();

        TasksResp resp = new TasksResp(sortedTasks);

        if(type.equals("bash")){
            return new ResponseEntity( resp.getCommands() , HttpStatus.OK);
        }else{
            return new ResponseEntity( resp.getTasks(), HttpStatus.OK);
        }
    }
}
