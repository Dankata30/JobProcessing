package com.yordanchorbadzhiyski.jobprocessing.utils;

import com.yordanchorbadzhiyski.jobprocessing.model.Task;

import java.util.*;

public class DAG {
    private Map<Integer, Task> taskMap;

    public DAG() {
        this.taskMap = new HashMap<>();
    }

    public void addTask(Task task) {
        task.setDependencies();
        taskMap.put(task.getId(), task);
    }

    public void buildDAG(List<Task> tasks) {
        for (Task task : tasks)
        {
            addTask(task);
        }

        for (Task task : tasks) {
            List<Integer> requiresIds = task.getRequires();

            if (requiresIds != null)
            {
                for (Integer requiresId : requiresIds)
                {
                    Task requiredTask = taskMap.get(requiresId);
                    if (requiredTask != null)
                    {
                        requiredTask.addDependency(task);
                    }
                }
            }
        }
    }

    // Example method to print the DAG
    public void printDAG() {
        for (Task task : taskMap.values()) {
            System.out.println("Task: " + task.getName() + " (ID: " + task.getId() + ")");
            System.out.println("Command: " + task.getCommand());
            List<Task> children = task.getDependencies();
            if (!children.isEmpty()) {
                System.out.println("Requires:");
                for (Task child : children) {
                    System.out.println("  - " + child.getName() + " (ID: " + child.getId() + ")");
                }
            }
            System.out.println();
        }
    }

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

}
