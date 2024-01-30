package com.yordanchorbadzhiyski.jobprocessing.exceptions;

/**
 * Checked exception class representing a cycle detected in a directed graph.
 * This exception is thrown when attempting to process a list of tasks that contains
 * circular dependencies, making it impossible to determine a valid execution order.
 *
 * The class extends {@code Throwable}, making it a checked exception. It provides
 * a constructor to set a custom error message.
 *
 * @author Yordan Chorbadzhiyski
 * @version 1.0
 * @since 2024-01-23
 */
public class CycleDetectedException extends Throwable {

    /**
     * Constructs a new CycleDetectedException with the specified error message.
     *
     * @param message The detail message indicating the reason for the exception.
     */
    public CycleDetectedException(String message) {
        super(message);
    }
}
