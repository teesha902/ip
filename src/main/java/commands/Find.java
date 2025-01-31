package commands;

import tasks.Task;
import java.util.ArrayList;

public class Find {
    public static String execute(String userInput, ArrayList<Task> tasks) {
        //get keyword
        //search through stored list
        // return or say none found

        String keyword = userInput.split(" ")[1].trim().toLowerCase();
        ArrayList<Task> matchingTasks = new ArrayList<>();

        // Search for tasks containing the keyword
        for (Task task : tasks) {
            if (task.getName().toLowerCase().contains(keyword)) {
                matchingTasks.add(task);
            }
        }

        // Handle no matches found
        if (matchingTasks.isEmpty()) {
            return "I couldn't find any tasks with the keyword: \"" + keyword + "\". Try a different one!";
        }

        // Format the results nicely
        StringBuilder result = new StringBuilder("Here are the tasks I found related to \"" + keyword + "\":\n");
        for (int i = 0; i < matchingTasks.size(); i++) {
            result.append(i + 1).append(". ").append(matchingTasks.get(i)).append("\n");
        }
        return result.toString().trim();

    }
}
