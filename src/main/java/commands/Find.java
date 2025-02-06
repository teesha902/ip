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
     * @param tasks The list of tasks to search through.
     * @param keywords The keywords to search for (supports multiple keywords via varargs).
     * @return A formatted string listing matching tasks or a message indicating no matches were found.
     */
    public static String execute(ArrayList<Task> tasks, String... keywords) {
        if (keywords.length == 0) {
            return "You forgot to tell me what keyword(s) to look for. Try again!";
        }
        Set<Task> matchingTasks = new HashSet<>(); // Unordered set for efficiency

        // make keywords lowercase for case-insensitive matching
        for (Task task : tasks) {
            String taskNameLower = task.getName().toLowerCase();
            for (String keyword : keywords) {
                if (taskNameLower.contains(keyword.toLowerCase())) {
                    matchingTasks.add(task);
                    break; // Avoid unnecessary checks once match is found
                }
            }
        }
        // Handle case where no tasks are found
        if (matchingTasks.isEmpty()) {
            return "I couldn't find any tasks related to the keywords: \""
                    + String.join("\", \"", keywords) + "\".\nTry different ones!";
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
