package com.yordanchorbadzhiyski.jobprocessing.model;

import java.util.List;

public class TasksResp {
    private List<Task> tasks;

    public TasksResp(List<Task> tasks){
        this.tasks = tasks;
    }

    public List<Task> getTasks(){
        return tasks;
    }
}
