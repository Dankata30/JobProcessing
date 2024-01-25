package com.yordanchorbadzhiyski.jobprocessing.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yordanchorbadzhiyski.jobprocessing.model.TaskHandler;

public class JSONParser implements CommonParser {
    @Override
    public TaskHandler ParseRequest(String request) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TaskHandler taskList = objectMapper.readValue(request, TaskHandler.class);

        return taskList;
    }
}
