package commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.PiggyException;
import tasks.Task;
import tasks.ToDo;

public class DeleteTaskTest {
    private ArrayList<Task> taskList;

    @BeforeEach
    void setUp() {
        taskList = new ArrayList<>();
    }

    @Test
    void execute_deleteFromEmptyList() {
        PiggyException e = assertThrows(PiggyException.class, () -> DeleteTask.execute("delete 1", taskList));
        assertEquals("You need to pick a task to delete that is actually in the list, silly.", e.getMessage());
    }

    @Test
    void execute_nonIntegerIndex() {
        taskList.add(new ToDo("Buy groceries")); // Add at least 1 task
        PiggyException e = assertThrows(PiggyException.class, () -> DeleteTask.execute("delete one", taskList));
        assertEquals("You need to pick a single index number to delete from the list. You can try again.",
                e.getMessage());
    }

    @Test
    void execute_outOfBoundsIndex() {
        taskList.add(new ToDo("Do homework"));
        PiggyException e = assertThrows(PiggyException.class, () -> DeleteTask.execute("delete 5", taskList));
        assertEquals("You need to pick a task to delete that is actually in the list, silly.", e.getMessage());
    }

    @Test
    void execute_correctDeletion() throws PiggyException {
        taskList.add(new ToDo("Walk the dog"));
        taskList.add(new ToDo("Go jogging"));
        taskList.add(new ToDo("Read a book"));

        String result = DeleteTask.execute("delete 2", taskList);
        String expected = "Phew! We got rid of [T][ ] Go jogging\nNow you have 2 tasks to worry about.";

        assertEquals(expected, result);
        assertEquals(2, taskList.size()); // Ensure size updated
    }

    @Test
    void execute_correctDeletionOneLeft() throws PiggyException {
        taskList.add(new ToDo("Submit assignment"));
        taskList.add(new ToDo("Pay bills"));

        String result = DeleteTask.execute("delete 2", taskList);
        String expected = "Phew! We got rid of [T][ ] Pay bills\nNow you only have 1 task to worry about.";

        assertEquals(expected, result);
        assertEquals(1, taskList.size()); // Ensure size updated
    }

    @Test
    void execute_correctDeletionAllDeleted() throws PiggyException {
        taskList.add(new ToDo("Cook dinner"));

        String result = DeleteTask.execute("delete 1", taskList);
        String expected = "Phew! We got rid of [T][ ] Cook dinner\nNow you have 0 tasks to worry about.";

        assertEquals(expected, result);
        assertEquals(0, taskList.size()); // Ensure list is now empty
    }
}
