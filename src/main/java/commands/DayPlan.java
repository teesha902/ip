package commands;

import exception.PiggyException;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import java.util.ArrayList;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a command that generates a day's plan based on tasks.
 * It checks for deadlines and events occurring on a specified date.
 */
public class DayPlan {

    /**
     * Executes the "agenda for {date}" command, retrieving tasks and events for a specified date.
     *
     * @param userInput The user's input string containing the date
     * @param taskList  The list of tasks to check against the specified date.
     * @return A formatted string of deadlines and events occurring on the specified date.
     * @throws PiggyException If the user input is invalid or contains an incorrect date format.
     */
    public static String execute(String userInput, ArrayList<Task> taskList) throws PiggyException {
        if (taskList.isEmpty()) {
            return "You have no tasks at the moment. Free all day!";
        }

        String[] inputParts = userInput.split(" "); // Split into command + date

        // Ensure the command has the correct format
        if (inputParts.length < 3 || !inputParts[1].equals("for")) {
            throw new PiggyException("I don't exactly understand what you are asking. Try this format:\n "
                    + "agenda for d/M/yyyy (e.g., agenda for 2/12/2023)");
        }

        // Extract the date
        String dateStr = inputParts[2].trim();

        // Check if date missing
        if (dateStr.isEmpty()) {
            throw new PiggyException("Missing date! Please provide a valid date "
                    + "in the format d/M/yyyy (e.g., 2/12/2023).");
        }

        LocalDate currDate;
        try {
            currDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("d/M/yyyy"));
        } catch (DateTimeParseException e) {
            throw new PiggyException("Invalid date! Please check the day, month, "
                    + "and format (d/M/yyyy, e.g., 2/12/2023).");
        }

        // Format output date
        String formattedDate = currDate.format(DateTimeFormatter.ofPattern("EEEE, MMM dd yyyy"));

        StringBuilder tasksOfDay = new StringBuilder("Here's what's happening on ")
                .append(formattedDate)
                .append(":\n\nDEADLINES:\n");

        // Iterate through list -> get all deadlines on given day
        int deadlineCount = 0;
        for (Task task : taskList) {
            if (task instanceof Deadline && ((Deadline) task).getDueDate().toLocalDate().equals(currDate)) {
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
            tasksOfDay.append("You have ").append(deadlineCount).append(" deadline")
                    .append(deadlineCount == 1 ? "" : "s")
                    .append(" on this day.\n");
        }

        tasksOfDay.append("\nEVENTS:\n");

        // Iterate through list -> get all events that cross given day
        int eventCount = 0;
        for (Task task : taskList) {
            if (task instanceof Event && ((Event) task).includesDate(currDate)) {
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
            tasksOfDay.append("You have ").append(eventCount).append(" event")
                    .append(eventCount == 1 ? "" : "s")
                    .append(" on this day.\n");
        }
        return tasksOfDay.toString().trim();
    }
}

