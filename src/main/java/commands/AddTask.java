package commands;

import tasks.Task;
import tasks.Deadline;
import tasks.Event;
import tasks.ToDo;
import exception.PiggyException;
import java.util.ArrayList;

public class AddTask {

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
        String dueDate = userInput.substring(dueDateIndex + 3).trim();
        validateNonEmpty(taskName, "You forgot to mention what the task is.");
        validateNonEmpty(dueDate, "You forgot to mention when the deadline it.");

        // Add the task to the task list
        Task newTask = new Deadline(taskName, dueDate);
        taskList.add(newTask);
        // Return success message
        return taskAddedMsg(newTask, taskList.size());

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
        String startTime = userInput.substring(fromIndex + 5, toIndex).trim();
        String endTime = userInput.substring(toIndex + 3).trim();
        //.trim() - only removes extra spaces at the beginning/end - not inside phrase
        validateNonEmpty(taskName, "You forgot to mention what the event is.");
        validateNonEmpty(startTime, "You forgot to mention when the event starts.");
        validateNonEmpty(endTime, "You forgot to mention when the event ends.");

        // Add the task to the task list
        Task newTask = new Event(taskName, startTime, endTime);
        taskList.add(newTask);
        // Return success message
        return taskAddedMsg(newTask, taskList.size());
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
        return "New task incoming! I've added it to our list :)\n  " + task + "\n" + taskCountMessage;
    }
}


