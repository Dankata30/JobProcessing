package com.yordanchorbadzhiyski.jobprocessing.service;

import com.yordanchorbadzhiyski.jobprocessing.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest

public class JobProcessingServiceTest {
    @Autowired
    private JobProcessingService jobProcessingService;

    @BeforeEach
    void setUp() {
        // Initialize tasks and requestObj before each test
        jobProcessingService.clear();
    }

    @Test
    public void checkAddTaskWorks(){
        Task task = new Task(1, "task-1", "dummy", List.of(2) );
        jobProcessingService.addTask(task);

        int expected = jobProcessingService.getTaskMap().size();
        int actual = 1;

        assertEquals(actual, expected);
    }

    @Test
    public void checkBuildDAGPopulatesMapOfTasks(){
        List<Task> tasks = new ArrayList<>(List.of(
                new Task(1, "task-1", "dummy1", new ArrayList<>()),
                new Task(2, "task-2", "dummy2", List.of(1)),
                new Task(3, "task-3", "dummy3", List.of(2))
        ));

        jobProcessingService.buildDAG(tasks);

        int expected = jobProcessingService.getTaskMap().size();
        int actual = 3;

        assertEquals(actual, expected);
    }

    @Test
    void testTopologicalSortWithMultipleTasks() {
        Task task1 = new Task(1, "Task-1", "Command-1", List.of());
        Task task2 = new Task(2, "Task-2", "Command-2", List.of(1));
        Task task3 = new Task(3, "Task-3", "Command-3", List.of(2));

        List<Task> tasks = new ArrayList<>(List.of(task3, task1, task2));

        jobProcessingService.buildDAG(tasks);
        List<Task> result = jobProcessingService.topologicalSort();

        List<Task> expectedResult = List.of(task1, task2, task3);
        assertEquals(expectedResult, result);
    }

    @Test
    void testTopologicalSortWithNoItems() {
        List<Task> result = jobProcessingService.topologicalSort();

        List<Task> expectedResult = new ArrayList<>();
        assertEquals(expectedResult, result);
    }

    @Test
    void testHasCycleWithCircularDependency() {
        // Mock data with a circular dependency
        Task task1 = new Task(1, "Task-1", "Command-1", List.of(2));
        Task task2 = new Task(2, "Task-2", "Command-2", List.of(1));

        // Arrange the mocked taskMap
        List<Task> tasks = new ArrayList<>(List.of(task1, task2));

        jobProcessingService.buildDAG(tasks);

        assertTrue(jobProcessingService.hasCycle());
    }

    @Test
    void testHasCycleWithoutCircularDependency() {
        // Mock data with a circular dependency
        Task task1 = new Task(1, "Task-1", "Command-1", List.of());
        Task task2 = new Task(2, "Task-2", "Command-2", List.of(1));

        // Arrange the mocked taskMap
        List<Task> tasks = new ArrayList<>(List.of(task1, task2));

        jobProcessingService.buildDAG(tasks);

        assertFalse(jobProcessingService.hasCycle());
    }

}
