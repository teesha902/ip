package commands;

/**
 * Handles the display of help information about available commands.
 */
public class Help {
    /**
     * Returns a formatted help message describing available commands.
     *
     * @return A string containing help information.
     */
    public static String execute() {
        return "Here are the commands you can use in PiggyPlanner:\n\n"
                + "1. list - View all tasks\n"
                + "2. mark [task number] - Mark a task as done\n"
                + "3. unmark [task number] - Mark a task as not done\n"
                + "4. todo [task description] - Add a ToDo task\n"
                + "5. deadline [task description] /by [d/M/yyyy HHmm] - Add a Deadline task\n"
                + "6. event [task description] /from [d/M/yyyy HHmm] /to [d/M/yyyy HHmm] - Add an Event task\n"
                + "7. delete [task number] - Delete a task\n"
                + "8. agenda for [d/M/yyyy] - View tasks on a specific date\n"
                + "9. find [keywords] - Search tasks by keywords\n"
                + "10. help - Show this help message\n"
                + "11. bye - Exit PiggyPlanner\n\n"
                + "For the full User Guide, visit:\n"
                + "👉 https://teesha902.github.io/ip/";
    }
}
