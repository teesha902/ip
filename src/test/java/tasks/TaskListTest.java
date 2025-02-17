package tasks;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.PiggyException;

public class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    void setUp() {
        taskList = new TaskList(new ArrayList<>());
    }

    @Test
    void isDuplicateTask_duplicateToDo() {
        ToDo todo = new ToDo("Buy milk");
        taskList.getAllTasks().add(todo);
        assert taskList.isDuplicateTask(new ToDo("Buy milk"));
    }

    @Test
    void isDuplicateTask_nonDuplicateToDo() {
        taskList.getAllTasks().add(new ToDo("Buy milk"));
        assert !taskList.isDuplicateTask(new ToDo("Call mom"));
    }

    @Test
    void isDuplicateTask_duplicateDeadline() {
        Deadline deadline = new Deadline("Submit report", LocalDateTime.of(2025, 2, 18, 23, 59));
        taskList.getAllTasks().add(deadline);

        assert taskList.isDuplicateTask(new Deadline("Submit report", LocalDateTime.of(2025, 2, 18, 23, 59)));
    }

    @Test
    void isDuplicateTask_nonDuplicateDeadline() {
        taskList.getAllTasks().add(new Deadline("Submit report", LocalDateTime.of(2025, 2, 18, 23, 59)));

        assert !taskList.isDuplicateTask(new Deadline("Submit assignment", LocalDateTime.of(2025, 2, 18, 23, 59)));
    }

    @Test
    void isDuplicateTask_duplicateEvent() throws PiggyException {
        Event event = new Event("Team meeting", LocalDateTime.of(2025, 2, 19, 10, 0),
                LocalDateTime.of(2025, 2, 19, 12, 0));
        taskList.getAllTasks().add(event);

        assert taskList.isDuplicateTask(new Event("Team meeting",
                LocalDateTime.of(2025, 2, 19, 10, 0), LocalDateTime.of(2025, 2, 19, 12, 0)));
    }

    @Test
    void isDuplicateTask_nonDuplicateEvent() throws PiggyException {
        taskList.getAllTasks().add(new Event("Team meeting", LocalDateTime.of(2025, 2, 19, 10, 0),
                LocalDateTime.of(2025, 2, 19, 12, 0)));

        assert !taskList.isDuplicateTask(new Event("Team lunch",
                LocalDateTime.of(2025, 2, 19, 12, 0), LocalDateTime.of(2025, 2, 19, 14, 0)));
    }
}
