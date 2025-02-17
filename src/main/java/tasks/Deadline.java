package tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline that must be completed before a specific date/time.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("EEEE, MMM dd yyyy, h:mma");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("h:mma");
    private final LocalDateTime dueDate;

    /**
     * Constructs a new Deadline task.
     *
     * @param name The description of the task.
     * @param dueDate The due date and time of the task.
     */
    public Deadline(String name, LocalDateTime dueDate) {
        super(name);
        this.dueDate = dueDate;
    }

    /**
     * Constructs a new Deadline task with completion status.
     *
     * @param name The description of the task.
     * @param dueDate The due date and time of the task.
     * @param isDone The completion status of the task.
     */
    public Deadline(String name, LocalDateTime dueDate, boolean isDone) {
        super(name);
        this.dueDate = dueDate;
        this.isDone = isDone;
    }

    /**
     * Gets the due date of the deadline.
     *
     * @return The due date as a LocalDateTime object.
     */
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    /**
     * Gets the due time formatted as a string.
     *
     * @return The formatted due time string.
     */
    public String getTime() {
        return dueDate.format(TIME_FORMATTER).toLowerCase();
    }

    /**
     * Returns a string representation of the Deadline task.
     *
     * @return A formatted string representing the deadline task.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + dueDate.format(OUTPUT_FORMATTER).toLowerCase() + ")";
    }

    /**
     * Compares this deadline with another object to determine equality.
     * Two deadlines are considered equal if they have the same description and due date.
     *
     * @param obj The object to compare with this deadline.
     * @return true if the object is a Deadline with the same description and due date; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Deadline deadline = (Deadline) obj;
        return this.dueDate.equals(deadline.dueDate);
    }
}
