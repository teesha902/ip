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
}