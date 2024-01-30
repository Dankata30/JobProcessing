package com.yordanchorbadzhiyski.jobprocessing.model;

import java.util.List;

public class TaskHandler {

    private List<Task> tasks;

    public TaskHandler(){

    }

    public TaskHandler(List<Task> tasks){
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
