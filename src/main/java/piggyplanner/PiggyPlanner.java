package piggyplanner;

import java.util.Scanner;

import commands.AddTask;
import commands.CommandType;
import commands.DayPlan;
import commands.DeleteTask;
import commands.Find;
import commands.Help;
import commands.ListCommand;
import commands.Mark;
import commands.Unmark;
import exception.PiggyException;
import storage.Storage;
import tasks.TaskList;
import ui.Ui;


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
        assert userInput != null : "User input should never be null";
        assert !userInput.trim().isEmpty() : "User input should never be empty";
        assert taskList != null : "Task list should not be null when processing a command";

        String[] inputParts = userInput.trim().split(" ", 2); // Split: command, arguments
        CommandType command = CommandType.fromString(inputParts[0]);
        assert command != null : "Command type should never be null in processCommand()";
        validateArguments(command, inputParts);

        switch (command) {
        case LIST:
            return ListCommand.execute(taskList.getAllTasks());

        case MARK:
            assert inputParts.length == 2 : command + " should have an argument";
            String markResponse = Mark.execute(userInput, taskList.getAllTasks());
            Storage.updateList(taskList.getAllTasks());
            return markResponse;

        case UNMARK:
            assert inputParts.length == 2 : command + " should have an argument";
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
            assert inputParts.length == 2 : command + " should have an argument";
            String deleteResponse = DeleteTask.execute(userInput, taskList.getAllTasks());
            Storage.updateList(taskList.getAllTasks());
            return deleteResponse;

        case FIND:
            assert inputParts.length >= 2 : command + " should have an argument";
            return Find.execute(userInput, taskList.getAllTasks());

        case DAYPLAN:
            assert inputParts.length >= 2 : command + " should have an argument";
            return DayPlan.execute(userInput, taskList.getAllTasks());
        case HELP:
            return Help.execute();
        case EXIT:
            //Platform.exit();
            return "Goodbye! See you soon! 🐷";


        case UNKNOWN:
        default:
            assert false : "processCommand should never receive an UNKNOWN command";
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
            return e.getMessage(); // Return error messages to GUI
        }
    }

    /**
     * Runs the PiggyPlanner application in the console.
     * Continuously listens for user commands until the user exits.
     */
    public void run() {
        Ui.showWelcomeMessage();

        while (true) {
            String userInput = reader.nextLine(); // Get user input from console

            assert userInput != null : "User input should never be null when reading from console";

            try {
                String response = processCommand(userInput);
                Ui.showMessage(response);

                if (CommandType.fromString(userInput.split(" ")[0]) == CommandType.EXIT) {
                    Ui.showExitMessage();
                    return; // Exit
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
        assert command != null : "Command type should never be null in validateArguments";
        int argLength = inputParts.length; // Check number of arguments

        switch (command) {
        case LIST:
        case EXIT:
            assert argLength == 1 : "LIST and EXIT commands should not have arguments";
            break;

        case MARK:
        case UNMARK:
        case DELETE:
            assert argLength == 2 : "MARK, UNMARK, and DELETE commands require exactly one argument";
            break;
        case FIND:
            assert argLength >= 2 : "FIND command should have at least one keyword";
            break;

        case TODO:
            assert argLength == 2 && !inputParts[1].trim().isEmpty()
                    : "TODO command must have a valid task description";
            break;

        case DEADLINE:
            assert argLength >= 2 && inputParts[1].contains("/by") : "DEADLINE command must have a valid date format";
            break;

        case EVENT:
            assert argLength >= 2 && inputParts[1].contains("/from") && inputParts[1].contains("/to")
                    : "EVENT command must have a valid start and end time";
            break;

        case DAYPLAN:
            assert argLength == 2 : "DAYPLAN command requires a date argument";
            break;

        case UNKNOWN:
        default:
            assert false : "validateArguments should never receive an UNKNOWN command";
            //throw new PiggyException("Unknown command. Can you try again?");
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
