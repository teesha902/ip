package tasks;

import java.util.ArrayList;

/**
 * Manages list of all tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with an existing list of tasks.
     *
     * @param tasks The list of tasks to initialize the TaskList with.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Gets all tasks in the task list.
     *
     * @return An ArrayList of Task objects.
     */
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    /**
     * Checks if a task with the same description and relevant time details
     * (if applicable) already exists in the task list.
     *
     * @param newTask The task to check for duplicates.
     * @return true if a task with the same details exists; false otherwise.
     */
    public boolean isDuplicateTask(Task newTask) {
        for (Task task : tasks) {
            if (task.equals(newTask)) {
                return true;
            }
        }
        return false;
    }
}
