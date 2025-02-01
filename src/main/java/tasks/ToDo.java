package tasks;

/**
 * Represents a simple task without any date/time.
 */
public class ToDo extends Task {

    /**
     * Constructs a new ToDo task.
     *
     * @param name The description of the task.
     */
    public ToDo(String name) {
        super(name);
    }

    /**
     * Constructs a new ToDo task with completion status.
     *
     * @param name The description of the task.
     * @param isDone The completion status of the task.
     */
    public ToDo(String name, boolean isDone) {
        super(name);
        this.isDone = isDone;
    }

    /**
     * Returns a string representation of the ToDo task.
     *
     * @return A formatted string representing the ToDo task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
