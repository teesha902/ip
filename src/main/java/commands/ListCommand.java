package commands;

import tasks.Task;
import java.util.ArrayList;

/**
 * Handles displaying all tasks in the list.
 */
public class ListCommand {
    /**
     * Displays all tasks in the list.
     *
     * @param taskList The list of tasks.
     * @return A formatted list of tasks or a message if the list is empty.
     */
    public static String execute(ArrayList<Task> taskList) {
        if (taskList.isEmpty()) {
            return "You have no tasks at the moment.";
        }
        StringBuilder fullList = new StringBuilder("To-do list:\n");
        for (int i = 0; i < taskList.size(); i++) {
            fullList.append((i + 1)).append(". ").append(taskList.get(i)).append("\n");
        }
        return fullList.toString().trim();
    }
}

