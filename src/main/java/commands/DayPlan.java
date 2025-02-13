package commands;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import exception.PiggyException;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;

/**
 * Represents a command that generates a day's plan based on tasks.
 * It checks for deadlines and events occurring on a specified date.
 */
public class DayPlan {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("EEEE, MMM dd yyyy");

    /**
     * Executes the "agenda for {date}" command, retrieving tasks and events for a specified date.
     *
     * @param userInput The user's input string containing the date
     * @param taskList  The list of tasks to check against the specified date.
     * @return A formatted string of deadlines and events occurring on the specified date.
     * @throws PiggyException If the user input is invalid or contains an incorrect date format.
     */
    public static String execute(String userInput, ArrayList<Task> taskList) throws PiggyException {
        assert userInput != null : "User input should never be null in DayPlan.execute()";
        assert taskList != null : "Task list should never be null in DayPlan.execute()";

        if (taskList.isEmpty()) {
            return "You have no tasks at the moment. Free all day!";
        }

        LocalDate currDate = extractDate(userInput);
        assert currDate != null : "Extracted date should never be null in DayPlan.execute()";

        String formattedDate = currDate.format(OUTPUT_FORMATTER);

        StringBuilder tasksOfDay = new StringBuilder("Here's what's happening on ")
                .append(formattedDate)
                .append(":\n\n");

        appendDeadlines(currDate, taskList, tasksOfDay);
        appendEvents(currDate, taskList, tasksOfDay);

        return tasksOfDay.toString().trim();
    }

    /**
     * Extracts and validates the date from user input.
     *
     * @param userInput The user's input string.
     * @return The extracted LocalDate.
     * @throws PiggyException If the input format is invalid.
     */
    private static LocalDate extractDate(String userInput) throws PiggyException {
        String[] inputParts = userInput.split(" ");

        if (inputParts.length < 3 || !inputParts[1].equals("for")) {
            throw new PiggyException("I don't exactly understand what you are asking. Try this format:\n "
                    + "agenda for d/M/yyyy (e.g., agenda for 2/12/2023)");
        }

        String dateStr = inputParts[2].trim();
        if (dateStr.isEmpty()) {
            throw new PiggyException("Missing date! Please provide a valid date "
                    + "in the format d/M/yyyy (e.g., 2/12/2023).");
        }

        try {
            return LocalDate.parse(dateStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new PiggyException("Invalid date! \nPlease check the day, month, "
                    + "and format (d/M/yyyy, e.g., 2/12/2023).");
        }
    }

    /**
     * Appends deadlines occurring on the specified date to the output.
     *
     * @param date      The date to filter deadlines.
     * @param taskList  The list of tasks.
     * @param tasksOfDay The StringBuilder storing the day's schedule.
     */
    private static void appendDeadlines(LocalDate date, ArrayList<Task> taskList, StringBuilder tasksOfDay) {
        tasksOfDay.append("DEADLINES:\n");
        int deadlineCount = 0;

        for (Task task : taskList) {
            if (task instanceof Deadline && ((Deadline) task).getDueDate().toLocalDate().equals(date)) {
                tasksOfDay.append(task.getName())
                        .append(" due at: ")
                        .append(((Deadline) task).getTime())
                        .append("\n");
                deadlineCount++;
            }
        }

        if (deadlineCount == 0) {
            tasksOfDay.append("You have no deadlines on this day.\n");
        } else {
            tasksOfDay.append("You have ").append(deadlineCount).append(" deadline");
            if (deadlineCount > 1) {
                tasksOfDay.append("s");
            }
            tasksOfDay.append(" on this day.\n");
        }
        tasksOfDay.append("\n");
    }

    /**
     * Appends events occurring on the specified date to the output.
     *
     * @param date The date to filter events.
     * @param taskList The list of tasks.
     * @param tasksOfDay The StringBuilder storing the day's schedule.
     */
    private static void appendEvents(LocalDate date, ArrayList<Task> taskList, StringBuilder tasksOfDay) {
        tasksOfDay.append("EVENTS:\n");
        int eventCount = 0;

        for (Task task : taskList) {
            if (task instanceof Event && ((Event) task).includesDate(date)) {
                tasksOfDay.append(task.getName())
                        .append(" ")
                        .append(((Event) task).getDates())
                        .append("\n");
                eventCount++;
            }
        }

        if (eventCount == 0) {
            tasksOfDay.append("You have no events on this day.\n");
        } else {
            tasksOfDay.append("You have ").append(eventCount).append(" event");
            if (eventCount > 1) {
                tasksOfDay.append("s");
            }
            tasksOfDay.append(" on this day.\n");
        }
    }
}


