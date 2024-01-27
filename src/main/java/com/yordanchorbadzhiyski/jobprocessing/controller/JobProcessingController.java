package com.yordanchorbadzhiyski.jobprocessing.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yordanchorbadzhiyski.jobprocessing.model.Response;
import com.yordanchorbadzhiyski.jobprocessing.model.Task;
import com.yordanchorbadzhiyski.jobprocessing.model.TaskHandler;
import com.yordanchorbadzhiyski.jobprocessing.utils.DAG;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/process")
public class JobProcessingController {

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<Response> processJson(@RequestBody TaskHandler tasks){
        DAG dag = new DAG();
        dag.buildDAG(tasks.getTasks());
        List<Task> sortedTasks = dag.topologicalSort();

        for (Task task : sortedTasks) {
            System.out.println("Task Name: " + task.getName());
            System.out.println("Task Command: " + task.getCommand());
            System.out.println();
        }

        return new ResponseEntity( sortedTasks, HttpStatus.OK);
    }


//    @PostMapping("")
//    @ResponseBody
//    public ResponseEntity<Response> processJson(@RequestBody String request) throws JsonProcessingException {
//
//        JSONParser parser = new JSONParser();
//        List<Task> tasks = parser.parseRequest(request).getTasks();
//
//        DAG dag = new DAG();
//        dag.buildDAG(tasks);
//        List<Task> sortedTasks = dag.topologicalSort();
//
//        String reply = "";
//
//        for (Task task : sortedTasks) {
//            reply+=task.getCommand()+"\n";
////            System.out.println("Task Id:" + task.getId());
//            System.out.println("Task Name: " + task.getName());
//            System.out.println("Task Command: " + task.getCommand());
////            System.out.println("Task Requires: " + task.getRequires());
//            System.out.println();
//        }
//
////        return ResponseEntity.ok(new Response(parser.buildResponse(sortedTasks)));
//        return new ResponseEntity( sortedTasks, HttpStatus.OK);
//    }

}
