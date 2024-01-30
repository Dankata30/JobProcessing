package com.yordanchorbadzhiyski.jobprocessing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a task in a job processing system. Tasks have an identifier, a name,
 * a command to be executed, and a list of required tasks that must be completed
 * before the current task can be executed. The class also provides methods for
 * managing dependencies between tasks.
 *
 * The identifier is automatically extracted from the task name based on a predefined
 * naming convention (e.g., "task-1" extracts an ID of 1).
 *
 * @author Yordan Chorbadzhiyski
 * @version 1.0
 * @since 2024-01-23
 */
public class Task {
    private int id;
    private String name;
    private String command;
    private List<Integer> requires;

    private List<Task> dependencies;

    /**
     * Default constructor for Task.
     */
    public Task() {
    }

    /**
     * Parameterized constructor for Task.
     *
     * @param id       The identifier of the task.
     * @param name     The name of the task.
     * @param command  The command to be executed for the task.
     * @param requires The list of required task names.
     */
    public Task(int id, String name, String command, List<Integer> requires) {
        this.id = id;
        this.name = name;
        this.command = command;
        this.requires = requires;
    }

    /**
     * Gets the name of the task.
     *
     * @return The name of the task.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the task and automatically updates the identifier.
     *
     * @param name The new name for the task.
     */
    public void setName(String name) {
        this.name = name;
        setId();
    }

    /**
     * Gets the command to be executed for the task.
     *
     * @return The command to be executed.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Sets the command to be executed for the task.
     *
     * @param command The command to be executed.
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * Gets the list of required task names.
     *
     * @return The list of required task names.
     */
    public List<Integer> getRequires() {
        return requires;
    }

    /**
     * Sets the list of required task names and converts them to their corresponding identifiers.
     *
     * @param requires The list of required task names.
     */
    public void setRequires(List<String> requires) {
        this.requires = new ArrayList<>();

        for (String taskName : requires) {
            this.requires.add(extractId(taskName));
        }
    }

    /**
     * Gets the identifier of the task.
     *
     * @return The identifier of the task.
     */
    public int getId() {
        return id;
    }

    /**
     * Automatically sets the identifier based on the task name.
     */
    public void setId() {
        this.id = extractId(getName());
    }

    /**
     * Extracts the identifier from a given task name.
     *
     * @param taskName The task name from which to extract the identifier.
     * @return The extracted identifier.
     */
    public int extractId(String taskName) {
        return Integer.parseInt(taskName.substring(taskName.indexOf("-") + 1));
    }

    /**
     * Gets the list of dependencies for the task.
     *
     * @return The list of task dependencies.
     */
    public List<Task> getDependencies() {
        return dependencies;
    }

    /**
     * Initializes the list of task dependencies.
     */
    public void setDependencies() {
        dependencies = new ArrayList<>();
    }

    /**
     * Adds a task dependency to the list.
     *
     * @param task The task to be added as a dependency.
     */
    public void addDependency(Task task) {
        dependencies.add(task);
    }
}

