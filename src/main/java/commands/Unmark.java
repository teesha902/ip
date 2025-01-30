package commands;

import tasks.Task;
import exception.PiggyException;
import java.util.ArrayList;

public class Unmark {
    public static String execute(String index, ArrayList<Task> taskList) throws PiggyException {
        try {
            int taskIndex = Integer.parseInt(index.split(" ")[1]) - 1;
            if (taskIndex < 0 || taskIndex >= taskList.size()) {
                throw new PiggyException("You need to unmark something actually in the list, silly");
            }

            Task currTask = taskList.get(taskIndex);

            // Check if the task is already marked
            if (currTask.status().equals(" ")) {
                return "The task is already unmarked.";
            } else {
                // Mark the task and return success message
                currTask.unmark();
                return "Oops, no problem." +
                        "\nI've unmarked the task:\n  " + currTask;
            }

        } catch (NumberFormatException e) {
            throw new PiggyException("You need to pick an index number to unmark in the list. You can try again.");
        }
    }
}
