package com.yordanchorbadzhiyski.jobprocessing.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.yordanchorbadzhiyski.jobprocessing.model.Task;
import com.yordanchorbadzhiyski.jobprocessing.model.TaskHandler;
import com.yordanchorbadzhiyski.jobprocessing.model.TaskMixIn;

import javax.sound.midi.Soundbank;
import java.util.List;

public class JSONParser implements CommonParser {
    @Override
    public TaskHandler parseRequest(String request) throws JsonProcessingException {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            TaskHandler taskList = objectMapper.readValue(request, TaskHandler.class);
            return taskList;
        }
        catch(JsonProcessingException e){
            System.out.println(e);
        }

        return null;
    }

    @Override
    public String buildResponse(List<Task> tasks) throws JsonProcessingException {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.addMixIn(Task.class, TaskMixIn.class);

            String json = objectMapper.writeValueAsString(tasks);
            System.out.println(json);
            return json;
        }catch (JsonProcessingException e){
            e.printStackTrace();
            return null;
        }
    }
}
