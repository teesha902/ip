package tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class DeadlineTest {
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    @Test
    public void getTime_correctlyFormatsAmTime() {
        LocalDateTime dueDate = LocalDateTime.parse("2/12/2025 0930", INPUT_FORMATTER);
        Deadline deadline = new Deadline("Submit report", dueDate);
        assertEquals("9:30am", deadline.getTime(), "getTime() should return time in h:mma format.");
    }

    @Test
    public void getTime_correctlyFormatsPmTime() {
        LocalDateTime dueDate = LocalDateTime.parse("2/12/2025 2130", INPUT_FORMATTER);
        Deadline deadline = new Deadline("Finish project", dueDate);
        assertEquals("9:30pm", deadline.getTime(), "getTime() should return time in h:mma format.");
    }

    @Test
    public void getTime_correctlyFormatsMidnight() {
        LocalDateTime dueDate = LocalDateTime.parse("2/12/2025 0000", INPUT_FORMATTER);
        Deadline deadline = new Deadline("Start new task", dueDate);
        assertEquals("12:00am", deadline.getTime(), "getTime() should correctly handle midnight.");
    }

    @Test
    public void getTime_correctlyFormatsNoon() {
        LocalDateTime dueDate = LocalDateTime.parse("2/12/2025 1200", INPUT_FORMATTER);
        Deadline deadline = new Deadline("Lunch break", dueDate);
        assertEquals("12:00pm", deadline.getTime(), "getTime() should correctly handle noon.");
    }
}
