package commands;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import tasks.Task;

/**
 * Represents a command to find tasks in the task list that match one or more keywords.
 */
public class Find {
    /**
     * Extracts keywords from user input and calls the varargs-based execute method.
     *
     * @param userInput The full user input string, including the "find" command and keywords.
     * @param tasks The list of tasks to search through.
     * @return A formatted string listing matching tasks or a message indicating no matches were found.
     */
    public static String execute(String userInput, ArrayList<Task> tasks) {
        // Ensure the command has arguments (keywords)
        String[] inputParts = userInput.trim().split(" ", 2);
        if (inputParts.length < 2 || inputParts[1].trim().isEmpty()) {
            return "You forgot to tell me what keyword(s) to look for. Try again!";
        }

        // Extract keywords and pass them as varargs
        String[] rawKeywords = inputParts[1].trim().split(" ");
        ArrayList<String> keywordList = new ArrayList<>();

        for (String word : rawKeywords) {
            if (!word.isEmpty()) {
                keywordList.add(word);
            }
        }
        String[] keywords = keywordList.toArray(new String[0]);
        return processKeywords(tasks, keywords);
    }

    /**
     * Searches for tasks that contain any of the provided keywords.
     *
     * @param tasks The list of tasks to search through.
     * @param keywords The keywords to search for (supports multiple keywords via varargs).
     * @return A formatted string listing matching tasks or a message indicating no matches were found.
     */
    public static String processKeywords(ArrayList<Task> tasks, String... keywords) {
        assert tasks != null : "Task list should never be null";
        assert keywords.length > 0 : "At least one keyword should be provided";

        Set<Task> matchingTasks = new HashSet<>();

        // Search for tasks that contain any of the keywords (case-insensitive)
        for (Task task : tasks) {
            String taskNameLower = task.getName().toLowerCase();
            for (String keyword : keywords) {
                if (taskNameLower.contains(keyword.toLowerCase())) {
                    matchingTasks.add(task);
                    break; // Stop checking after first match
                }
            }
        }

        // Handle case where no tasks match
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
