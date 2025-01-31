package tasks;

import exception.PiggyException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

/**
 * Represents an event task that has a start and end date/time.
 */
public class Event extends Task {
    private final LocalDateTime start;
    private final LocalDateTime end;
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("EEEE, MMM dd yyyy, h:mma");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("EEEE, MMM dd yyyy");

    /**
     * Constructs a new Event task.
     *
     * @param name The description of the event.
     * @param start The start date and time of the event.
     * @param end The end date and time of the event.
     * @throws PiggyException If the start time is after the end time.
     */
    public Event(String name, LocalDateTime start, LocalDateTime end) throws PiggyException {
        super(name);
        if (!isValidTimeRange(start, end)) {
            throw new PiggyException("Event start time must be before end time.");
        }
        this.start = start;
        this.end = end;
    }

    /**
     * Constructs a new Event task with completion status.
     *
     * @param name The description of the event.
     * @param start The start date and time of the event.
     * @param end The end date and time of the event.
     * @param isDone The completion status of the event.
     * @throws PiggyException If the start time is after the end time.
     */
    public Event(String name, LocalDateTime start, LocalDateTime end, boolean isDone) throws PiggyException {
        super(name);
        if (!isValidTimeRange(start, end)) {
            throw new PiggyException("Event start time must be before end time.");
        }
        this.start = start;
        this.end = end;
        this.isDone = isDone;
    }

    /**
     * Validates that the event's start time is before the end time.
     *
     * @param start The start time of the event.
     * @param end The end time of the event.
     * @return True if start time is before end time, otherwise false.
     */
    private static boolean isValidTimeRange(LocalDateTime start, LocalDateTime end) {
        return start.isBefore(end);
    }

    /**
     * Gets the start time of the event.
     *
     * @return The start time as a LocalDateTime object.
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Gets the end time of the event.
     *
     * @return The end time as a LocalDateTime object.
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Checks if the event includes a given date.
     *
     * @param date The date to check.
     * @return True if the event includes the given date, otherwise false.
     */
    public boolean includesDate(LocalDate date) {
        return !date.isBefore(start.toLocalDate()) && !date.isAfter(end.toLocalDate());
    }

    /**
     * Gets the formatted event dates as a string.
     *
     * @return The formatted event dates.
     */
    public String getDates() {
        return "from: " + start.format(DATE_FORMATTER) + ", to: " + end.format(DATE_FORMATTER);
    }

    /**
     * Returns a string representation of the Event task.
     *
     * @return A formatted string representing the event task.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start.format(OUTPUT_FORMATTER).toLowerCase() +
                " to: " + end.format(OUTPUT_FORMATTER).toLowerCase() + ")";
    }
}