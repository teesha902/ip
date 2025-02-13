package commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        assert userInput != null : "User input should not be null in Find.execute()";
        assert tasks != null : "Task list should not be null in Find.execute()";

        String[] inputParts = userInput.trim().split(" ", 2);
        if (inputParts.length < 2 || inputParts[1].trim().isEmpty()) {
            return "You forgot to tell me what keyword(s) to look for. Try again!";
        }
        /*
        // Extract keywords and pass them as varargs
        String[] rawKeywords = inputParts[1].trim().split(" ");
        ArrayList<String> keywordList = new ArrayList<>();

        for (String word : rawKeywords) {
            if (!word.isEmpty()) {
                keywordList.add(word);
            }
        }
        String[] keywords = keywordList.toArray(new String[0]);
        */
        // Extract keywords as a List (filters out any empty words)
        List<String> keywords = extractKeywords(inputParts[1]);
        return processKeywords(tasks, keywords);
    }

    /**
     * Processes the search for tasks containing any of the provided keywords.
     *
     * @param tasks The list of tasks.
     * @param keywords The keywords to search for.
     * @return The formatted search results.
     */
    private static String processKeywords(ArrayList<Task> tasks, List<String> keywords) {
        assert !keywords.isEmpty() : "Keyword list should not be empty in Find.processKeywords()";

        // Stream-based filtering of matching tasks
        Set<Task> matchingTasks = tasks.stream()
                .filter(task -> taskMatchesKeywords(task, keywords))
                .collect(Collectors.toSet());

        return formatResults(matchingTasks, keywords);
    }

    /**
     * Checks if a task's name contains any of the provided keywords (case-insensitive).
     */
    private static boolean taskMatchesKeywords(Task task, List<String> keywords) {
        String taskNameLower = task.getName().toLowerCase();

        return keywords.stream()
                .map(keyword -> keyword.toLowerCase())
                .anyMatch(keyword -> taskNameLower.contains(keyword));
    }

    /**
     * Extracts keywords from a string (splits by spaces, removes empties).
     */
    private static List<String> extractKeywords(String input) {
        return List.of(input.split(" ")).stream()
                .filter(word -> !word.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * Formats the result string based on matching tasks.
     */
    private static String formatResults(Set<Task> matchingTasks, List<String> keywords) {
        if (matchingTasks.isEmpty()) {
            return "I couldn't find any tasks related to the keywords: \""
                    + String.join("\", \"", keywords) + "\".\nTry different ones!";
        }

        StringBuilder result = new StringBuilder("Here are the tasks I found related to the keywords: "
                + String.join(", ", keywords) + ":\n");

        int count = 1;
        for (Task task : matchingTasks) {
            result.append(count++).append(". ").append(task).append("\n");
        }

        return result.toString().trim();
    }
}
