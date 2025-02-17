package piggyplanner;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.PiggyException;
import storage.Storage;
import tasks.Task;
import tasks.ToDo;

public class PiggyPlannerTest {

    private PiggyPlanner piggyPlanner;

    @BeforeEach
    void setUp() throws PiggyException {
        ArrayList<Task> initialTasks = new ArrayList<>();
        initialTasks.add(new ToDo("Test Task"));
        Storage.updateList(initialTasks); // Ensure the file starts with this known state

        piggyPlanner = new PiggyPlanner();
    }

    @Test
    void getResponse_listCommand_returnsListOfTasks() {
        String response = piggyPlanner.getResponse("list");
        assert (response.contains("Test Task"));
    }

    @Test
    void getResponse_todoCommand_addsTask() {
        String response = piggyPlanner.getResponse("todo Buy milk");
        assert (response.contains("Buy milk"));
    }

    @Test
    void getResponse_deadlineCommand_addsDeadlineTask() {
        String response = piggyPlanner.getResponse("deadline Submit report /by 18/2/2025 2359");
        assert (response.contains("Submit report"));
    }

    @Test
    void getResponse_eventCommand_addsEventTask() {
        String response = piggyPlanner.getResponse("event Team meeting /from 18/2/2025 1400 /to 18/2/2025 1600");
        assert (response.contains("Team meeting"));
    }

    @Test
    void getResponse_markCommand_marksTaskAsDone() {
        piggyPlanner.getResponse("todo Complete project");
        String response = piggyPlanner.getResponse("mark 2");
        assert (response.contains("[X] Complete project"));
    }

    @Test
    void getResponse_unmarkCommand_unmarksTask() {
        piggyPlanner.getResponse("todo Complete project");
        piggyPlanner.getResponse("mark 2");
        String response = piggyPlanner.getResponse("unmark 2");
        assert (response.contains("[ ] Complete project"));
    }


    @Test
    void getResponse_findCommand_findsTasksWithKeyword() {
        String response = piggyPlanner.getResponse("find Test");
        assert (response.contains("Test Task"));
    }

    @Test
    void getResponse_dayPlanCommand_showsTasksForDate() {
        piggyPlanner.getResponse("deadline Submit report /by 18/2/2025 2359");
        piggyPlanner.getResponse("event Team meeting /from 18/2/2025 1400 /to 18/2/2025 1600");

        String response = piggyPlanner.getResponse("agenda for 18/2/2025");
        assert (response.contains("Submit report"));
        assert (response.contains("Team meeting"));
    }

}
