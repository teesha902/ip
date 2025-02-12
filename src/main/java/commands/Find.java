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
        String[] keywords = extractKeywords(userInput);
        if (keywords.length == 0) {
            return handleNoKeywords();
        }
        Set<Task> matchedTasks = filterMatchingTasks(tasks, keywords);
        return formatSearchResults(matchedTasks, keywords);
    }

    /**
     * Extracts keywords from the user input.
     *
     * @param userInput The full user command containing keywords.
     * @return An array of extracted keywords.
     */
    private static String[] extractKeywords(String userInput) {
        String[] parts = userInput.trim().split(" ", 2);
        return (parts.length < 2) ? new String[0] : parts[1].split("\\s+");
    }

    /**
     * Returns a message when no keywords are provided.
     *
     * @return An error message prompting the user to enter keywords.
     */
    private static String handleNoKeywords() {
        return "You forgot to tell me what keyword(s) to look for. Try again!";
    }

    /**
     * Filters tasks based on matching keywords.
     *
     * @param tasks The list of tasks.
     * @param keywords The keywords to search for.
     * @return A set of tasks that contain at least one of the keywords.
     */
    private static Set<Task> filterMatchingTasks(ArrayList<Task> tasks, String... keywords) { // Using varargs here
        Set<Task> matchedTasks = new HashSet<>();
        for (Task task : tasks) {
            if (taskMatchesKeywords(task, keywords)) {
                matchedTasks.add(task);
            }
        }
        return matchedTasks;
    }

    /**
     * Checks if a task's name contains any of the given keywords.
     *
     * @param task The task to check.
     * @param keywords The keywords to search for.
     * @return True if the task name contains any of the keywords, false otherwise.
     */
    private static boolean taskMatchesKeywords(Task task, String[] keywords) {
        String taskNameLower = task.getName().toLowerCase();
        for (String keyword : keywords) {
            if (taskNameLower.contains(keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Formats the search results into a user-friendly string.
     *
     * @param matchedTasks The set of tasks that match the keywords.
     * @param keywords The keywords that were searched for.
     * @return A formatted string listing the matching tasks.
     */
    private static String formatSearchResults(Set<Task> matchedTasks, String[] keywords) {
        if (matchedTasks.isEmpty()) {
            return "I couldn't find any tasks related to the keywords: \""
                    + String.join("\", \"", keywords) + "\".\nTry different ones!";
        }
        StringBuilder result = new StringBuilder("Here are the tasks I found related to the keywords: "
                + String.join(", ", keywords) + ":\n");
        int count = 1;
        for (Task task : matchedTasks) {
            result.append(count++).append(". ").append(task).append("\n");
        }
        return result.toString().trim();
    }
}

