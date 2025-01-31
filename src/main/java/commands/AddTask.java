package commands;

import tasks.Task;
import tasks.Deadline;
import tasks.Event;
import tasks.ToDo;
import exception.PiggyException;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles adding different types of tasks (ToDo, Deadline, Event) to the task list.
 */
public class AddTask {
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    /**
     * Adds a ToDo type task to the task list.
     *
     * @param userInput The full user command containing the task description.
     * @param taskList The list where the new task will be added.
     * @return A success message confirming the addition.
     * @throws PiggyException If the task description is missing.
     */
    public static String todo(String userInput, ArrayList<Task> taskList) throws PiggyException {
        if (userInput.length() <= 5) {
            throw new PiggyException("You forgot to mention what the task is.");
        }
        String taskName = userInput.substring(5).trim();
        validateNonEmpty(taskName, "You forgot to mention what the task is.");
        Task newTask = new ToDo(taskName);
        taskList.add(newTask);
        return taskAddedMsg(newTask, taskList.size());
    }

    /**
     * Adds a Deadline type task to the task list.
     *
     * @param userInput The full user command containing the task and due date.
     * @param taskList The list where the new task will be added.
     * @return A success message confirming the addition.
     * @throws PiggyException If the task description or deadline is missing or incorrectly formatted.
     */
    public static String deadline(String userInput, ArrayList<Task> taskList) throws PiggyException {
        if (!userInput.contains("/by")) {
            throw new PiggyException("You forgot to mention when the task is due.");
        }
        int dueDateIndex = userInput.indexOf("/by");
        String taskName = userInput.substring(9, dueDateIndex).trim();
        String dueDateStr = userInput.substring(dueDateIndex + 3).trim();
        validateNonEmpty(taskName, "You forgot to mention what the task is.");
        validateNonEmpty(dueDateStr, "You forgot to mention when the deadline is.");

        try {
            LocalDateTime dueDate = LocalDateTime.parse(dueDateStr, INPUT_FORMATTER);
            Task newTask = new Deadline(taskName, dueDate);
            taskList.add(newTask);
            return taskAddedMsg(newTask, taskList.size());
        } catch (Exception e) {
            throw new PiggyException("Invalid date format! Try again and use: d/M/yyyy HHmm (e.g., 2/12/2019 1800).");
        }
    }

    /**
     * Adds an Event type task to the task list.
     *
     * @param userInput The full user command containing the task, start time, and end time.
     * @param taskList The list where the new task will be added.
     * @return A success message confirming the addition.
     * @throws PiggyException If the task description, start time, or end time is missing or incorrectly formatted.
     */
    public static String event(String userInput, ArrayList<Task> taskList) throws PiggyException {
        if (!userInput.contains("/from") || !userInput.contains("/to")) {
            throw new PiggyException("You forgot to mention when the event starts/ends.");
        }
        int fromIndex = userInput.indexOf("/from");
        int toIndex = userInput.indexOf("/to");
        String taskName = userInput.substring(6, fromIndex).trim();
        String startTimeStr = userInput.substring(fromIndex + 5, toIndex).trim();
        String endTimeStr = userInput.substring(toIndex + 3).trim();
        validateNonEmpty(taskName, "You forgot to mention what the event is.");
        validateNonEmpty(startTimeStr, "You forgot to mention when the event starts.");
        validateNonEmpty(endTimeStr, "You forgot to mention when the event ends.");

        try {
            LocalDateTime startTime = LocalDateTime.parse(startTimeStr, INPUT_FORMATTER);
            LocalDateTime endTime = LocalDateTime.parse(endTimeStr, INPUT_FORMATTER);
            Task newTask = new Event(taskName, startTime, endTime);
            taskList.add(newTask);
            return taskAddedMsg(newTask, taskList.size());
        } catch (Exception e) {
            throw new PiggyException("Invalid date format! Try again and use: d/M/yyyy HHmm (e.g., 2/12/2019 1800).\n" +
                    "Or check that the start time is before the end time.");
        }
    }

    /**
     * Validates that the given input is not empty.
     *
     * @param input The input string to validate.
     * @param errorMessage The error message to display if validation fails.
     * @throws PiggyException If the input is empty or null.
     */
    private static void validateNonEmpty(String input, String errorMessage) throws PiggyException {
        if (input == null || input.trim().isEmpty()) {
            throw new PiggyException(errorMessage);
        }
    }

    /**
     * Constructs a formatted success message when a task is added.
     *
     * @param task The newly added task.
     * @param totalTasks The total number of tasks in the list.
     * @return A formatted success message.
     */
    private static String taskAddedMsg(Task task, int totalTasks) {
        // Create the message based on the total number of tasks
        String taskCountMessage;
        if (totalTasks == 1) {
            taskCountMessage = "Now we have 1 task in the list.";
        } else {
            taskCountMessage = "Now we have " + totalTasks + " tasks in the list.";
        }
        // Return the final success message
        return "New task incoming! I've added it to our list :)\n " + task + "\n" + taskCountMessage;
    }
}


