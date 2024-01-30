package com.yordanchorbadzhiyski.jobprocessing.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a response object for a list of tasks in a job processing system.
 * It encapsulates a list of TaskResp objects and provides methods to retrieve
 * them, along with a consolidated command string.
 *
 * The class is designed to convert a list of Task objects into a list of TaskResp objects
 * and consolidate their commands into a single command string.
 *
 * @author Yordan Chorbadzhiyski
 * @version 1.0
 * @since 2024-01-23
 */
public class TasksResp {

    private List<TaskResp> tasks;
    private String commands;

    /**
     * Constructs a new TasksResp with the specified list of tasks.
     *
     * @param tasks The list of tasks to be processed and converted into TaskResp objects.
     */
    public TasksResp(List<Task> tasks) {
        this.tasks = new ArrayList<>();
        this.commands = "";

        for (Task task : tasks) {
            this.tasks.add(new TaskResp(task.getName(), task.getCommand()));
            addCommand(task.getCommand());
        }
    }

    /**
     * Gets the list of TaskResp objects representing the tasks.
     *
     * @return The list of TaskResp objects.
     */
    public List<TaskResp> getTasks() {
        return tasks;
    }

    /**
     * Adds a command to the consolidated command string.
     *
     * @param command The command to be added.
     */
    private void addCommand(String command) {
        this.commands += command + "\n";
    }

    /**
     * Gets the consolidated command string containing all task commands.
     *
     * @return The consolidated command string.
     */
    public String getCommands() {
        return this.commands;
    }
}

