package com.yordanchorbadzhiyski.jobprocessing.model;

public class TaskResp {
    private String name;
    private String command;


    public TaskResp(String name, String command) {
        this.name = name;
        this.command = command;
    }

    public String getName(){
        return this.name;
    }

    public String getCommand(){
        return this.command;
    }
}
