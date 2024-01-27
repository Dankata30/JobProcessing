package com.yordanchorbadzhiyski.jobprocessing.model;

import java.util.ArrayList;
import java.util.List;

public class TasksResp {
    private List<TaskResp> tasks;

    private String commands;

    public TasksResp(List<Task> tasks){
        this.tasks = new ArrayList<>();
        this.commands = "";

        for(Task task: tasks) {
            this.tasks.add(new TaskResp(task.getName(), task.getCommand()));
            addCommand(task.getCommand());
        }
    }

    public List<TaskResp> getTasks(){
        return tasks;
    }

    private void addCommand(String command){
        this.commands+=command + "\n";
    }

    public String getCommands(){
        return this.commands;
    }
}
