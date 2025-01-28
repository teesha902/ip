package tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//example: deadline return book /by 2/12/2019 1800
public class Deadline extends Task{
    //tasks that need to be done before a specific date/time
    //protected String due;
    private LocalDateTime dueDate;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("EEEE, MMM dd yyyy, h:mma");
    //EEEE → Full day name and h:mma → 12-hour format with AM/PM

    /*
    public Deadline(String name, String dueDate) {
        super(name);
        this.dueDate = LocalDateTime.parse(dueDate, INPUT_FORMATTER); // Convert input string to LocalDateTime
    }
    */

    public Deadline(String name, LocalDateTime dueDate) {
        super(name);
        this.dueDate = dueDate;
    }

    public Deadline(String name, LocalDateTime dueDate, boolean isDone) {
        super(name);
        //this.due = due;
        this.dueDate = dueDate;
        this.isDone = isDone;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + dueDate.format(OUTPUT_FORMATTER).toLowerCase() + ")";
    }
}
