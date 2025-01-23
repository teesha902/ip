package commands;

import tasks.Task;
import exception.PiggyException;
import java.util.ArrayList;

public class DeleteTask {
    public static String execute(String index, ArrayList<Task> taskList) throws PiggyException {
        try {
            int taskIndex = Integer.parseInt(index.split(" ")[1]) - 1;
            if (taskIndex < 0 || taskIndex >= taskList.size()) {
                throw new PiggyException("You need to pick a task to delete that is actually IN the list, silly");
            }
            Task currTask = taskList.get(taskIndex);
            taskList.remove(taskIndex);

            // Return success message
            if (taskList.size() == 1) {
                return "Phew! We got ride of " + currTask +
                        "\nNow you only have 1 task to worry about.";
            } else {
                return "Phew! We got ride of " + currTask +
                        "\nNow you have " + taskList.size() + " tasks to worry about.";
            }

        } catch (NumberFormatException e) {
            throw new PiggyException("You need to pick an index number to mark in the list. You can try again.");
        }
    }
}
