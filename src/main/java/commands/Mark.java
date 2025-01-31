package commands;

import tasks.Task;
import exception.PiggyException;
import java.util.ArrayList;

/**
 * Handles marking a task as completed in the task list.
 */
public class Mark {
    /**
     * Marks a task as completed based on the provided index.
     *
     * @param index The command containing the task index.
     * @param taskList The list of tasks.
     * @return A success message after marking the task.
     * @throws PiggyException If the index is invalid or the task is already marked.
     */
    public static String execute(String index, ArrayList<Task> taskList) throws PiggyException {
        try {
            int taskIndex = Integer.parseInt(index.split(" ")[1]) - 1;
            if (taskIndex < 0 || taskIndex >= taskList.size()) {
                throw new PiggyException("You need to mark something actually in the list, silly");
            }

            Task currTask = taskList.get(taskIndex);

            // Check if the task is already marked
            if (currTask.status().equals("X")) {
                return "The task is already marked.";
            } else {
                // Mark the task and return success message
                currTask.mark();

                return "Good work! Let's keep going." +
                        "\nI've marked this task as done:\n  " + currTask;
            }

        } catch (NumberFormatException e) {
            throw new PiggyException("You need to pick an index number to mark in the list. You can try again.");
        }
    }
}

