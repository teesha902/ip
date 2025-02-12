package commands;

import java.util.ArrayList;

import exception.PiggyException;
import tasks.Task;



/**
 * Handles deleting a task from the task list.
 */
public class DeleteTask {
    /**
     * Deletes a task from the list based on the provided index.
     *
     * @param index The command containing the task index.
     * @param taskList The list of tasks.
     * @return A success message after deletion.
     * @throws PiggyException If the index is invalid.
     */
    public static String execute(String index, ArrayList<Task> taskList) throws PiggyException {
        try {
            int taskIndex = Integer.parseInt(index.split(" ")[1]) - 1;
            if (taskIndex < 0 || taskIndex >= taskList.size()) {
                throw new PiggyException("You need to pick a task to delete that is actually in the list, silly.");
            }
            Task currTask = taskList.get(taskIndex);
            taskList.remove(taskIndex);
            if (taskList.size() == 1) {
                return "Phew! We got rid of " + currTask
                        + "\nNow you only have 1 task to worry about.";
            }
            return "Phew! We got rid of " + currTask
                    + "\nNow you have " + taskList.size() + " tasks to worry about.";
        } catch (NumberFormatException e) {
            throw new PiggyException("You need to pick a single index number "
                    + "to delete from the list. You can try again.");
        }
    }
}
