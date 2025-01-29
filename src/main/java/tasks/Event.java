package tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

//example: deadline return book /from 2/12/2019 1800 /to 2/12/2019 1800
public class Event extends Task{
    // tasks that start at a specific date/time and ends at a specific date/time
    private final LocalDateTime start;
    private final LocalDateTime end;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("EEEE, MMM dd yyyy, h:mma");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("EEEE, MMM dd yyyy");


    public Event(String name, LocalDateTime start, LocalDateTime end) {
        super(name);
        this.start = start;
        this.end = end;
    }

    public Event(String name, LocalDateTime start, LocalDateTime end, boolean isDone) {
        super(name);
        this.start = start;
        this.end = end;
        this.isDone = isDone;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public boolean includesDate(LocalDate date) {
        return !date.isBefore(start.toLocalDate()) && !date.isAfter(end.toLocalDate());
    }

    public String getDates() {
        return "starting on: " + start.format(DATE_FORMATTER) + ", till: " + end.format(DATE_FORMATTER);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start.format(OUTPUT_FORMATTER).toLowerCase() +
                " to: " + end.format(OUTPUT_FORMATTER).toLowerCase() + ")";
    }
}
