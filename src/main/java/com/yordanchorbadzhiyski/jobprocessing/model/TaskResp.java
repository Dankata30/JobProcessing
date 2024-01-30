package com.yordanchorbadzhiyski.jobprocessing.model;

/**
 * Represents a response object for a task in a job processing system.
 * It encapsulates the name and command attributes of a task and provides
 * methods to retrieve them.
 *
 * @author Yordan Chorbadzhiyski
 * @version 1.0
 * @since 2024-01-23
 */
public class TaskResp {

    private String name;
    private String command;

    /**
     * Constructs a new TaskResp with the specified name and command.
     *
     * @param name    The name of the task.
     * @param command The command associated with the task.
     */
    public TaskResp(String name, String command) {
        this.name = name;
        this.command = command;
    }

    /**
     * Gets the name of the task.
     *
     * @return The name of the task.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the command associated with the task.
     *
     * @return The command associated with the task.
     */
    public String getCommand() {
        return this.command;
    }
}

