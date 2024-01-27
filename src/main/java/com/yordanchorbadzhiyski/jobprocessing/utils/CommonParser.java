package com.yordanchorbadzhiyski.jobprocessing.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yordanchorbadzhiyski.jobprocessing.model.Task;
import com.yordanchorbadzhiyski.jobprocessing.model.TaskHandler;

import java.util.List;

public interface CommonParser {
    public TaskHandler parseRequest(String request) throws JsonProcessingException;

    public String buildResponse(List<Task> tasks) throws JsonProcessingException;
}
