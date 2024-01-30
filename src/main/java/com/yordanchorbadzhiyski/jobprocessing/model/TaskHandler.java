package com.yordanchorbadzhiyski.jobprocessing.model;

import java.util.List;

/**
 * Represents a handler for a list of tasks in a job processing system.
 * It encapsulates a list of tasks and provides methods to retrieve and set
 * the list of tasks.
 *
 * @author Yordan Chorbadzhiyski
 * @version 1.0
 * @since 2024-01-23
 */
public class TaskHandler {

    private List<Task> tasks;

    /**
     * Default constructor for TaskHandler.
     */
    public TaskHandler() {
    }

    /**
     * Parameterized constructor for TaskHandler.
     *
     * @param tasks The list of tasks to be handled.
     */
    public TaskHandler(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Gets the list of tasks.
     *
     * @return The list of tasks.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Sets the list of tasks.
     *
     * @param tasks The new list of tasks.
     */
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}

