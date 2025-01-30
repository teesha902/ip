package commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Task;
import tasks.Deadline;
import tasks.Event;
import exception.PiggyException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DayPlanTest {
    private ArrayList<Task> taskList;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    @BeforeEach
    void setUp() {
        taskList = new ArrayList<>();
    }

    @Test
    void noTasks() throws PiggyException {
        String result = DayPlan.execute("agenda for 10/2/2025", taskList);
        assertEquals("You have no tasks at the moment. Free all day!", result);
    }

    @Test
    void variousTasksInDay() throws PiggyException {
        taskList.add(new Deadline("Submit proposal", LocalDateTime.parse("10/2/2025 2359", INPUT_FORMATTER)));
        taskList.add(new Event("Conference",
                LocalDateTime.parse("10/2/2025 0900", INPUT_FORMATTER),
                LocalDateTime.parse("10/2/2025 1700", INPUT_FORMATTER)));

        String expectedOutput = "Here's what's happening on Monday, Feb 10 2025:\n" +
                "\nDEADLINES:\nSubmit proposal due at: 11:59pm\nYou have 1 deadline on this day.\n" +
                "\nEVENTS:\nConference from: Monday, Feb 10 2025, to: Monday, Feb 10 2025\n" +
                "You have 1 event on this day.";

        String result = DayPlan.execute("agenda for 10/2/2025", taskList);
        assertEquals(expectedOutput, result);
    }
}