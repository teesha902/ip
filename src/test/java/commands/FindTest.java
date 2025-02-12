package commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.PiggyException;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.ToDo;



public class FindTest {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private ArrayList<Task> taskList;

    @BeforeEach
    void setUp() throws PiggyException {
        taskList = new ArrayList<>();
        taskList.add(new ToDo("Buy milk"));
        taskList.add(new ToDo("Call John"));
        taskList.add(new Deadline("Submit report", LocalDateTime.parse("10/2/2025 2359", FORMATTER)));
        taskList.add(new Event("Team meeting", LocalDateTime.parse("15/2/2025 1000", FORMATTER),
                LocalDateTime.parse("15/2/2025 1200", FORMATTER)));
    }

    @Test
    void findSingleMatch() {
        String result = Find.execute("find milk", taskList);
        String expected = "Here are the tasks I found related to the keywords: milk:\n"
                + "1. [T][ ] Buy milk";
        assertEquals(expected, result);
    }

    @Test
    void findMultipleMatches() {
        String result = Find.execute("find meeting report", taskList);
        String expected = "Here are the tasks I found related to the keywords: meeting, report:\n"
                + "1. [E][ ] Team meeting (from: saturday, feb 15 2025, 10:00am to: saturday, feb 15 2025, 12:00pm)\n"
                + "2. [D][ ] Submit report (by: monday, feb 10 2025, 11:59pm)";
        assertEquals(expected, result);
    }

    @Test
    void noMatch() {
        String result = Find.execute("find groceries", taskList);
        assertEquals("I couldn't find any tasks related to the keywords: \"groceries\".\nTry different ones!", result);
    }

    @Test
    void findWithMultipleKeywordsOneMatching() {
        String result = Find.execute("find groceries meeting", taskList);
        String expected = "Here are the tasks I found related to the keywords: groceries, meeting:\n"
                + "1. [E][ ] Team meeting (from: saturday, feb 15 2025, 10:00am to: saturday, feb 15 2025, 12:00pm)";
        assertEquals(expected, result);
    }
}
