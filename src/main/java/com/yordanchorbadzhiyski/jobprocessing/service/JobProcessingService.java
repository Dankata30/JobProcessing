package com.yordanchorbadzhiyski.jobprocessing.service;

import com.yordanchorbadzhiyski.jobprocessing.model.Task;
import org.springframework.stereotype.Service;

import java.util.*;

import java.util.*;

/**
 * Service class responsible for processing tasks, building a Directed Acyclic Graph (DAG)
 * based on task dependencies, performing topological sorting, and checking for circular dependencies.
 * It uses a map to store tasks, where the key is the task ID.
 *
 * The service provides methods to add tasks, build a DAG, perform topological sorting,
 * check for circular dependencies, and clear the task map.
 *
 * @author Yordan Chorbadzhiyski
 * @version 1.0
 * @since 2024-01-23
 */
@Service
public class JobProcessingService {

    private Map<Integer, Task> taskMap;

    /**
     * Constructs a new JobProcessingService with an empty task map.
     */
    public JobProcessingService() {
        this.taskMap = new HashMap<>();
    }

    /**
     * Gets the task map.
     *
     * @return The task map.
     */
    public Map<Integer, Task> getTaskMap() {
        return taskMap;
    }

    /**
     * Adds a task to the task map and initializes its dependencies.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        task.setDependencies();
        taskMap.put(task.getId(), task);
    }

    /**
     * Builds a Directed Acyclic Graph (DAG) based on the provided list of tasks.
     * Initializes dependencies for each task.
     *
     * @param tasks The list of tasks.
     */
    public void buildDAG(List<Task> tasks) {
        for (Task task : tasks) {
            addTask(task);
        }

        for (Task task : tasks) {
            List<Integer> requiresIds = task.getRequires();

            if (requiresIds != null) {
                for (Integer requiresId : requiresIds) {
                    Task requiredTask = taskMap.get(requiresId);
                    if (requiredTask != null) {
                        requiredTask.addDependency(task);
                    }
                }
            }
        }
    }

    /**
     * Performs topological sorting on the DAG and returns the sorted list of tasks.
     *
     * @return The list of tasks in topologically sorted order.
     */
    public List<Task> topologicalSort() {
        Stack<Task> stack = new Stack<>();
        Map<Integer, Boolean> visited = new HashMap<>();

        // Initialize visited map
        for (Task task : taskMap.values()) {
            visited.put(task.getId(), false);
        }

        // Perform DFS
        for (Task task : taskMap.values()) {
            if (!visited.get(task.getId())) {
                topologicalSortUtil(task, visited, stack);
            }
        }

        // Pop elements from the stack to get the topological order
        List<Task> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }

        return result;
    }

    private void topologicalSortUtil(Task task, Map<Integer, Boolean> visited, Stack<Task> stack) {
        visited.put(task.getId(), true);

        for (Task child : task.getDependencies()) {
            if (!visited.get(child.getId())) {
                topologicalSortUtil(child, visited, stack);
            }
        }

        // Push the current task onto the stack after visiting all its children
        stack.push(task);
    }

    /**
     * Checks if circular dependencies are present in the DAG.
     *
     * @return True if circular dependencies are present, false otherwise.
     */
    public boolean hasCycle() {
        Map<Integer, Boolean> visited = new HashMap<>();
        Map<Integer, Boolean> recursionStack = new HashMap<>();

        // Initialize visited and recursionStack maps
        for (Task task : taskMap.values()) {
            visited.put(task.getId(), false);
            recursionStack.put(task.getId(), false);
        }

        // Perform DFS to detect cycles
        for (Task task : taskMap.values()) {
            if (!visited.get(task.getId())) {
                if (hasCycleUtil(task, visited, recursionStack)) {
                    return true; // Cycle detected
                }
            }
        }

        return false; // No cycle detected
    }

    private boolean hasCycleUtil(Task task, Map<Integer, Boolean> visited, Map<Integer, Boolean> recursionStack) {
        visited.put(task.getId(), true);
        recursionStack.put(task.getId(), true);

        for (Task child : task.getDependencies()) {
            if (!visited.get(child.getId())) {
                if (hasCycleUtil(child, visited, recursionStack)) {
                    return true; // Cycle detected
                }
            } else if (recursionStack.get(child.getId())) {
                return true; // Cycle detected
            }
        }

        recursionStack.put(task.getId(), false);
        return false;
    }

    /**
     * Clears the task map, removing all tasks.
     */
    public void clear() {
        this.taskMap = new HashMap<>();
    }
}

