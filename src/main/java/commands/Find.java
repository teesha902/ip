package commands;

import tasks.Task;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a command to find tasks in the task list that match one or more keywords.
 */
public class Find {
    /**
     * Searches for tasks that contain any of the provided keywords.
     *
     * @param userInput The input command containing the keyword(s) to search for.
     * @param tasks The list of tasks to search through.
     * @return A formatted string listing matching tasks or a message indicating no matches were found.
     */
    public static String execute(String userInput, ArrayList<Task> tasks) {
        // Check if at least one keyword is provided
        String[] inputParts = userInput.split(" ", 2);

        // Extract keywords and store them in a HashSet for quick lookups
        String[] keywords = inputParts[1].trim().toLowerCase().split("\\s+");
        Set<Task> matchingTasks = new HashSet<>(); // Unordered set for efficiency

        // Search for tasks that contain any of the keywords
        for (Task task : tasks) {
            String taskNameLower = task.getName().toLowerCase();
            for (String keyword : keywords) {
                if (taskNameLower.contains(keyword)) {
                    matchingTasks.add(task);
                    break; // Avoid unnecessary checks once a match is found
                }
            }
        }

        // Handle case where no tasks are found
        if (matchingTasks.isEmpty()) {
            return "I couldn't find any related to the keywords: \""
                    + String.join("\", ", keywords) + " \nTry different ones!";
        }

        // Format and return the results
        StringBuilder result = new StringBuilder("Here are the tasks I found related to the keywords: "
                + String.join(", ", keywords) + ":\n");
        int count = 1;
        for (Task task : matchingTasks) {
            result.append(count++).append(". ").append(task).append("\n");
        }
        return result.toString().trim();
    }
}
