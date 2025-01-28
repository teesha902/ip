package tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//example: deadline return book /from 2/12/2019 1800 /to 2/12/2019 1800
public class Event extends Task{
    // tasks that start at a specific date/time and ends at a specific date/time
    //protected String  start;
    //protected String end;
    private LocalDateTime start;
    private LocalDateTime end;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("EEEE, MMM dd yyyy, h:mma");

    /*
    public Event(String name, String start, String end) {
        super(name);
        this.start = LocalDateTime.parse(start, INPUT_FORMATTER);
        this.end = LocalDateTime.parse(end, INPUT_FORMATTER);
    }
    */

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

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start.format(OUTPUT_FORMATTER).toLowerCase() +
                " to: " + end.format(OUTPUT_FORMATTER).toLowerCase() + ")";
}
