package commands;

import tasks.Task;
import tasks.Deadline;
import tasks.Event;
import java.util.ArrayList;
import exception.PiggyException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// look between to and from - for events
public class DayPlan {
    public static String execute(String userInput, ArrayList<Task> taskList) throws PiggyException {
        if (taskList.isEmpty()) {
            return "You have no tasks at the moment. Free all day!";
        } else {
            String[] inputParts = userInput.split(" ", 2); // Split into command and date
            if (inputParts.length < 2 || inputParts[1].trim().isEmpty()) {
                return "You forgot to mention the which day! Please provide a date in the format: d/M/yyyy (e.g., 2/12/2023).";
            }
            String dateStr = inputParts[2];
            LocalDate currDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("d/M/yyyy"));

            StringBuilder tasksOfDay = new StringBuilder("Here's what's happening on "
                    + currDate.format(DateTimeFormatter.ofPattern("EEEE, MMM dd yyyy")) + ":\n")
                    .append("\nDEADLINES: \n");

            //iterate through list to get all deadlines on given day
            int deadlineCount = 0;
            for (Task task : taskList) {
                if (task instanceof Deadline && ((Deadline) task).getDueDate().toLocalDate().equals(currDate)) {
                    tasksOfDay.append(task.getName())
                            .append(((Deadline) task).getTime())
                            .append("\n");
                    deadlineCount++;
                }
            }

            if (deadlineCount == 0) {
                tasksOfDay.append("You have no deadlines on this day. \n");
            } else if (deadlineCount == 1) {
                tasksOfDay.append("You have 1 deadline on this day. \n");
            } else {
                tasksOfDay.append("You have ").append(deadlineCount).append(" deadlines on this day. \n");
            }

            tasksOfDay.append("EVENTS: \n");

            //iterate through list to get all events that cross given day
            int eventCount = 0;
            for (Task task : taskList) {
                if (task instanceof Event && ((Event) task).includesDate(currDate)) {
                    tasksOfDay.append(task.getName())
                            .append(((Event) task).getDates())
                            .append("\n");
                    eventCount++;
                }
            }

            if (eventCount == 0) {
                tasksOfDay.append("You have no events on this day. \n");
            } else if (eventCount == 1) {
                tasksOfDay.append("You have 1 event on this day. \n");
            } else {
                tasksOfDay.append("You have ").append(eventCount).append(" events on this day. \n");
            }

            return (tasksOfDay.toString().trim());

        }
    }
}
