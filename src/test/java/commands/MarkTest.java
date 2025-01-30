package commands;

import exception.PiggyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Task;
import tasks.ToDo;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MarkTest {
    private ArrayList<Task> taskList;

    @BeforeEach
    void setUp() {
        taskList = new ArrayList<>();
    }


    @Test
    void execute_nonIntegerIndex() {
        taskList.add(new ToDo("Buy groceries"));
        PiggyException e = assertThrows(PiggyException.class, () -> Mark.execute("mark one", taskList));
        assertEquals("You need to pick an index number to mark in the list. You can try again.", e.getMessage());
    }

    @Test
    void execute_outOfBoundsIndex() {
        taskList.add(new ToDo("Buy groceries"));
        PiggyException e = assertThrows(PiggyException.class, () -> Mark.execute("mark 2", taskList));
        assertEquals("You need to mark something actually in the list, silly", e.getMessage());
    }

    @Test
    void execute_markInEmptyList() {
        PiggyException e = assertThrows(PiggyException.class, () -> Mark.execute("mark 1", taskList));
        assertEquals("You need to mark something actually in the list, silly", e.getMessage());
    }

    @Test
    void execute_markAlrMarked() throws PiggyException {
        taskList.add(new ToDo("Buy pen"));
        Mark.execute("mark 1", taskList);
        assertEquals("The task is already marked.", Mark.execute("mark 1", taskList));
    }
    //correct test

    @Test
    void execute_markSuccess() throws PiggyException {
        taskList.add(new ToDo("Buy pen"));
        assertEquals("Good work! Let's keep going." +
                "\nI've marked this task as done:\n  [T][X] Buy pen",
                Mark.execute("mark 1", taskList));
    }

}