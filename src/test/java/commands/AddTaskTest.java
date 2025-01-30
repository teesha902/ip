package commands;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import tasks.Task;
import exception.PiggyException;

import java.util.ArrayList;

public class AddTaskTest {
    private ArrayList<Task> taskList;

    @BeforeEach
    void setUp() {
        taskList = new ArrayList<>();
    }

    @Test
    void validToDo() throws PiggyException {
        String response = AddTask.todo("todo Buy groceries", taskList);
        assertEquals("New task incoming! I've added it to our list :)\n " +
                "[T][ ] Buy groceries\nNow we have 1 task in the list.", response);
    }

    @Test
    void missingToDoTaskDescription() {
        PiggyException thrown = assertThrows(PiggyException.class, () -> {
            AddTask.todo("todo", taskList);
        });
        assertEquals("You forgot to mention what the task is.", thrown.getMessage());
    }

    @Test
    void validDeadlineInput() throws PiggyException {
        String response = AddTask.deadline("deadline Submit report /by 12/3/2025 1800", taskList);
        assertEquals("New task incoming! I've added it to our list :)\n " +
                "[D][ ] Submit report (by: wednesday, mar 12 2025, 6:00pm)\nNow we have 1 task in the list.", response);
    }


    @Test
    void missingDeadlineDescription() {
        PiggyException thrown = assertThrows(PiggyException.class, () -> {
            AddTask.deadline("deadline /by 12/3/2025 1800", taskList);
        });
        assertEquals("You forgot to mention what the task is.", thrown.getMessage());
    }

    @Test
    void missingDeadlineDate() {
        PiggyException thrown = assertThrows(PiggyException.class, () -> {
            AddTask.deadline("deadline Submit report", taskList);
        });
        assertEquals("You forgot to mention when the task is due.", thrown.getMessage());
    }

    @Test
    void invalidDeadlineFormat() {
        PiggyException thrown = assertThrows(PiggyException.class, () -> {
            AddTask.deadline("deadline Submit report /by 32/13/2025 1800", taskList);
        });
        assertEquals("Invalid date format! Try again and use: d/M/yyyy HHmm (e.g., 2/12/2019 1800).", thrown.getMessage());
    }

    @Test
    void validEventInput() throws PiggyException {
        String response = AddTask.event("event Team meeting /from 1/4/2025 0900 /to 1/4/2025 1100", taskList);
        assertEquals("New task incoming! I've added it to our list :)\n " +
                "[E][ ] Team meeting (from: tuesday, apr 01 2025, 9:00am to: tuesday, apr 01 2025, 11:00am)\nNow we have 1 task in the list.", response);
    }

    @Test
    void missingEventName() {
        PiggyException thrown = assertThrows(PiggyException.class, () -> {
            AddTask.event("event /from 1/4/2025 0900 /to 1/4/2025 1100", taskList);
        });
        assertEquals("You forgot to mention what the event is.", thrown.getMessage());
    }

    @Test
    void missingEventStart() {
        PiggyException thrown = assertThrows(PiggyException.class, () -> {
            AddTask.event("event Conference /to 1/4/2025 1100", taskList);
        });
        assertEquals("You forgot to mention when the event starts/ends.", thrown.getMessage());
    }

    @Test
    void missingEventEnd() {
        PiggyException thrown = assertThrows(PiggyException.class, () -> {
            AddTask.event("event Conference /from 1/4/2025 0900", taskList);
        });
        assertEquals("You forgot to mention when the event starts/ends.", thrown.getMessage());
    }

    @Test
    void invalidEventFormat() {
        PiggyException thrown = assertThrows(PiggyException.class, () -> {
            AddTask.event("event Conference /from 32/13/2025 0900 /to 1/4/2025 1100", taskList);
        });
        assertEquals("Invalid date format! Try again and use: d/M/yyyy HHmm (e.g., 2/12/2019 1800)." +
                "\nOr check that the start time is before the end time:", thrown.getMessage());
    }

    @Test
    void invalidEventTimeRange() {
        PiggyException thrown = assertThrows(PiggyException.class, () -> {
            AddTask.event("event Conference /from 2/4/2025 1800 /to 1/4/2025 0900", taskList);
        });
        assertEquals("Invalid date format! Try again and use: d/M/yyyy HHmm (e.g., 2/12/2019 1800)." +
                "\nOr check that the start time is before the end time:", thrown.getMessage());
    }
}

