package tasks;

/**
 * Represents a generic task that can be tracked in the task list.
 */
public class Task {
    protected final String name;
    protected boolean isDone;

    /**
     * Constructs a new Task.
     *
     * @param name The description of the task.
     */
    public Task(String name) {
        this.name = name;
        this.isDone = false;
    }

    /**
     * Gets the status of the task (done or not).
     *
     * @return "X" if done, otherwise " ".
     */
    public String status() {
        if (isDone) {
            return "X";
        }
        return " ";
    }

    /**
     * Marks the task as completed.
     */
    public void mark() {
        isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void unmark() {
        isDone = false;
    }

    /**
     * Gets the name/description of the task.
     *
     * @return The task name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns a string representation of the task.
     *
     * @return A formatted string representing the task.
     */
    @Override
    public String toString() {
        return "[" + this.status() + "] " + this.name;
    }

    /**
     * Compares this task with another object to determine equality.
     * Two tasks are considered equal if they have the same description (name),
     * regardless of their type or completion status.
     *
     * @param obj The object to compare with this task.
     * @return true if the object is a Task with the same description; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Task task = (Task) obj;
        return this.name.equals(task.name);
    }
}

