package com.yordanchorbadzhiyski.jobprocessing.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/process")
public class JobProcessingController {

    @GetMapping("{id}")
    public String greeting(@PathVariable String id){
        return change(id);
    }

    private static String change(String s){
        return "3";
    }
}
