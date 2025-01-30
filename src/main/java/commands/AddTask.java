package commands;

import tasks.Task;
import tasks.Deadline;
import tasks.Event;
import tasks.ToDo;
import exception.PiggyException;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddTask {
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    public static String todo(String userInput, ArrayList<Task> taskList) throws PiggyException {
        if (userInput.length() <= 5) {
            throw new PiggyException("You forgot to mention what the task is.");
        }
        String taskName = userInput.substring(5).trim();
        validateNonEmpty(taskName, "You forgot to mention what the task is.");
        // Add the task to the task list
        Task newTask = new ToDo(taskName);
        taskList.add(newTask);
        // Return success message
        return taskAddedMsg(newTask, taskList.size());
    }

    public static String deadline(String userInput, ArrayList<Task> taskList) throws PiggyException {
        if (!userInput.contains("/by")) {
            throw new PiggyException("You forgot to mention when the task is due.");
        }
        int dueDateIndex = userInput.indexOf("/by");
        if (dueDateIndex == -1) {
            throw new PiggyException("You forgot to mention when the task is due");
        }

        String taskName = userInput.substring(9, dueDateIndex).trim();
        String dueDateStr = userInput.substring(dueDateIndex + 3).trim();
        validateNonEmpty(taskName, "You forgot to mention what the task is.");
        validateNonEmpty(dueDateStr, "You forgot to mention when the deadline it.");

        try {
            LocalDateTime dueDate = LocalDateTime.parse(dueDateStr, INPUT_FORMATTER); // New: Convert String to LocalDateTime
            Task newTask = new Deadline(taskName, dueDate);
            taskList.add(newTask);
            return taskAddedMsg(newTask, taskList.size());
        } catch (Exception e) {
            throw new PiggyException("Invalid date format! Try again and use: d/M/yyyy HHmm (e.g., 2/12/2019 1800).");
        }
    }

    public static String event(String userInput, ArrayList<Task> taskList) throws PiggyException {
        if (!userInput.contains("/from") || !userInput.contains("/to")) {
            throw new PiggyException("You forgot to mention when the event starts/ends.");
        }
        int fromIndex = userInput.indexOf("/from");
        int toIndex = userInput.indexOf("/to");
        if (fromIndex == -1 || toIndex == -1) {
            throw new PiggyException("You forgot to mention when the event starts/ends.");
        }

        // Extract the task details
        String taskName = userInput.substring(6, fromIndex).trim();
        String startTimeStr = userInput.substring(fromIndex + 5, toIndex).trim();
        String endTimeStr = userInput.substring(toIndex + 3).trim();
        //.trim() - only removes extra spaces at the beginning/end - not inside phrase
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
                    "Or check that the start time is before the end time:");
        }

    }

    // Helper method to validate non-empty input
    private static void validateNonEmpty(String input, String errorMessage) throws PiggyException {
        if (input == null || input.trim().isEmpty()) {
            throw new PiggyException(errorMessage);
        }
    }

    // Format success message
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


