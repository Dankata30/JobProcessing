package com.yordanchorbadzhiyski.jobprocessing.service;

import com.yordanchorbadzhiyski.jobprocessing.model.Task;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JobProcessingService {
    private Map<Integer, Task> taskMap;

    public JobProcessingService() {
        this.taskMap = new HashMap<>();
    }

    public Map<Integer, Task> getTaskMap() {
        return taskMap;
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

//    ------------------------------ Checks if there are any problems with cycles
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

    public void clear() {
        this.taskMap = new HashMap<>();
    }
}
