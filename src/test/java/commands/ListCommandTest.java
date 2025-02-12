package commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.PiggyException;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.ToDo;


public class ListCommandTest {
    private ArrayList<Task> taskList;

    @BeforeEach
    void setUp() {
        taskList = new ArrayList<>();
    }

    @Test
    void execute_ifEmptyTaskList() {
        assertEquals("You have no tasks at the moment. Yay!", ListCommand.execute(taskList));
    }

    @Test
    void execute_multipleTasks_returnsFormattedList() throws PiggyException {
        taskList.add(new ToDo("Buy milk"));
        taskList.add(new Deadline("Submit report", LocalDateTime.of(2025, 2, 10, 18, 0)));
        taskList.add(new Event("Meeting", LocalDateTime.of(2025, 3, 1, 9, 0), LocalDateTime.of(2025, 3, 1, 11, 0)));

        String expected = "Here is our to-do list:\n"
                + "1. [T][ ] Buy milk\n"
                + "2. [D][ ] Submit report (by: monday, feb 10 2025, 6:00pm)\n"
                + "3. [E][ ] Meeting (from: saturday, mar 01 2025, 9:00am to: saturday, mar 01 2025, 11:00am)";

        assertEquals(expected, ListCommand.execute(taskList));
    }

}

