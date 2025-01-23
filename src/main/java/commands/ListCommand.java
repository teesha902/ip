package commands;

import tasks.Task;
import java.util.ArrayList;

public class ListCommand {
    public static String execute(ArrayList<Task> taskList) {
        if (taskList.isEmpty()) {
            return "You have no tasks at the moment.";
        } else {
            StringBuilder fullList = new StringBuilder("To-do list:\n");
            for (int i = 0; i < taskList.size(); i++) {
                fullList.append((i + 1)) // 1-based index
                        .append(". ")
                        .append(taskList.get(i)) // Calls the Task class's toString() method
                        .append("\n");
            }
            return (fullList.toString().trim());
        }
    }
}

