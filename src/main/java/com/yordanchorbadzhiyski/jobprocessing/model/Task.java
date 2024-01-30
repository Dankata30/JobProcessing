package com.yordanchorbadzhiyski.jobprocessing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Task {
    private int id;
    private String name;
    private String command;
    private List<Integer> requires;

    private List<Task> dependencies;

    public Task(){

    }

    public Task(int id, String name, String command, List<Integer> requires){
        this.id = id;
        this.name = name;
        this.command = command;
        this.requires = requires;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setId();
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }


    public List<Integer> getRequires() {
        return requires;
    }

    public void setRequires(List<String> requires) {
        this.requires = new ArrayList<>();

        for(String taskName: requires){
            this.requires.add(extractId(taskName));
        }
    }

    public int getId() {
        return id;
    }

    public void setId() {
        this.id = extractId(getName());
    }

    public int extractId(String taskName){
        return Integer.parseInt(taskName.substring(taskName.indexOf("-") + 1));
    }


    public List<Task> getDependencies(){
        return dependencies;
    }

    public void setDependencies(){
        dependencies = new ArrayList<>();
    }

    public void addDependency(Task task){
        dependencies.add(task);
    }
}
