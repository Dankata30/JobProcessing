package com.yordanchorbadzhiyski.jobprocessing.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yordanchorbadzhiyski.jobprocessing.model.TaskHandler;

public interface CommonParser {
    public TaskHandler ParseRequest(String request) throws JsonProcessingException;
}
