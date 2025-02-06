package piggyplanner;

import commands.AddTask;
import commands.CommandType;
import commands.DayPlan;
import commands.DeleteTask;
import commands.Find;
import commands.ListCommand;
import commands.Mark;
import commands.Unmark;
import exception.PiggyException;
import storage.Storage;
import tasks.TaskList;
import ui.Ui;
import java.util.Scanner;

/**
 * The main class for the PiggyPlanner application.
 * This class validates and processes commands.
 */
public class PiggyPlanner {
    private final TaskList taskList;
    private final Scanner reader;

    /**
     * Constructs a new PiggyPlanner instance.
     * Initializes the task list by loading stored tasks and sets up a scanner for user input.
     *
     * @throws PiggyException if there is an error loading the stored tasks.
     */
    public PiggyPlanner() throws PiggyException {
        this.taskList = new TaskList(Storage.loadList());
        this.reader = new Scanner(System.in);
    }

    /**
     * Processes the user's command and returns the result.
     *
     * @param userInput The input command string from the user.
     * @return The result message after executing the command.
     * @throws PiggyException If an invalid command or incorrect arguments are provided.
     */
    private String processCommand(String userInput) throws PiggyException {
        String[] inputParts = userInput.trim().split(" ", 2);  // Split: command, arguments
        CommandType command = CommandType.fromString(inputParts[0]);

        validateArguments(command, inputParts);

        switch (command) {
        case LIST:
            return ListCommand.execute(taskList.getAllTasks());

        case MARK:
            String markResponse = Mark.execute(userInput, taskList.getAllTasks());
            Storage.updateList(taskList.getAllTasks());
            return markResponse;

        case UNMARK:
            String unmarkResponse = Unmark.execute(userInput, taskList.getAllTasks());
            Storage.updateList(taskList.getAllTasks());
            return unmarkResponse;

        case TODO:
            String todoResponse = AddTask.todo(userInput, taskList.getAllTasks());
            Storage.updateList(taskList.getAllTasks());
            return todoResponse;

        case DEADLINE:
            String deadlineResponse = AddTask.deadline(userInput, taskList.getAllTasks());
            Storage.updateList(taskList.getAllTasks());
            return deadlineResponse;

        case EVENT:
            String eventResponse = AddTask.event(userInput, taskList.getAllTasks());
            Storage.updateList(taskList.getAllTasks());
            return eventResponse;

        case DELETE:
            String deleteResponse = DeleteTask.execute(userInput, taskList.getAllTasks());
            Storage.updateList(taskList.getAllTasks());
            return deleteResponse;

        case FIND:
            return Find.execute(userInput, taskList.getAllTasks());

        case DAYPLAN:
            return DayPlan.execute(userInput, taskList.getAllTasks());

        case EXIT:
            //Platform.exit();
            return "Goodbye! See you soon! 🐷";


        case UNKNOWN:
        default:
            throw new PiggyException("Unfortunately, I don't know what that means. Please try again.");
        }
    }

    /**
     * Handles user input from the GUI and returns the response to be displayed.
     *
     * @param input The user's input string.
     * @return The response string from PiggyPlanner.
     */
    public String getResponse(String input) {
        try {
            return processCommand(input);
        } catch (PiggyException e) {
            return e.getMessage();  // Return error messages to GUI
        }
    }

    /**
     * Runs the PiggyPlanner application in the console.
     * Continuously listens for user commands until the user exits.
     */
    public void run() {
        Ui.showWelcomeMessage();

        while (true) {
            String userInput = reader.nextLine();  // Get user input from console

            try {
                String response = processCommand(userInput);
                Ui.showMessage(response);

                if (CommandType.fromString(userInput.split(" ")[0]) == CommandType.EXIT) {
                    Ui.showExitMessage();
                    return;  // Exit
                }

            } catch (PiggyException e) {
                Ui.showMessage(e.getMessage());
            }
        }
    }



    /**
     * Validates the arguments provided for a given command.
     * Ensures the user input meets the expected format for each command.
     *
     * @param command The command type provided by the user.
     * @param inputParts The split user input containing command and arguments.
     * @throws PiggyException if the provided arguments are incorrect or missing.
     */
    private void validateArguments(CommandType command, String[] inputParts) throws PiggyException {
        int argLength = inputParts.length; // Check number of arguments

        switch (command) {
        case LIST:
        case EXIT:
            if (argLength != 1) {
                throw new PiggyException("The '" + command.toString().toLowerCase()
                        + "' command does not take any arguments.");
            }
            break;

        case MARK:
        case UNMARK:
        case DELETE:
            if (argLength != 2) {
                throw new PiggyException("The '" + command.toString().toLowerCase()
                        + "' command requires exactly one task number.");
            }
            break;
        case FIND:
            if (argLength < 2 || inputParts[1].trim().isEmpty()) {
                throw new PiggyException("You forgot to tell me what keyword(s) to look for. Try again!");
            }
            break;

        case TODO:
            if (argLength != 2 || inputParts[1].trim().isEmpty()) {
                throw new PiggyException("The 'todo' command requires a task description.");
            }
            break;

        case DEADLINE:
            if (argLength < 2 || !inputParts[1].contains("/by")) {
                throw new PiggyException("The 'deadline' command requires a task description and a due date. "
                        + "Format: deadline <task> /by <d/M/yyyy HHmm>");
            }
            break;

        case EVENT:
            if (argLength < 2 || !inputParts[1].contains("/from") || !inputParts[1].contains("/to")) {
                throw new PiggyException("The 'event' command requires a task description, start date, and end date. "
                        + "Format: event <task> /from <d/M/yyyy HHmm> /to <d/M/yyyy HHmm>");
            }
            break;

        case DAYPLAN:
            if (argLength != 2) {
                throw new PiggyException("The 'agenda' command requires a valid date. Format: agenda for <d/M/yyyy>");
            }
            break;

        case UNKNOWN:
        default:
            throw new PiggyException("Unknown command. Can you try again?");
        }
    }

    /**
     * The entry point for the PiggyPlanner application.
     * Initializes and runs the application.
     *
     * @param args Command-line arguments (not used yet).
     */
    public static void main(String[] args) {
        try {
            new PiggyPlanner().run();
        } catch (PiggyException e) {
            System.out.println("An error occurred while starting the program: " + e.getMessage());
        }
    }


}
