import commands.CommandType;
import commands.AddTask;
import commands.DeleteTask;
import commands.ListCommand;
import commands.Mark;
import commands.Unmark;
import commands.DayPlan;
import exception.PiggyException;
import tasks.Task;
import storage.Storage;
import ui.Ui;
import tasks.TaskList;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * The main class for the PiggyPlanner application.
 * This class handles user interactions, processes commands, and manages tasks.
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
     * Runs the PiggyPlanner application.
     * Displays a welcome message and continuously listens for user commands until the user exits.
     */
    public void run() {
        Ui.showWelcomeMessage();

        while (true) {
            String userInput = reader.nextLine();
            String[] inputParts = userInput.split(" "); // Split into command + arguments
            CommandType command = CommandType.fromString(inputParts[0]);


            try {
                validateArguments(command, inputParts); //validate input format before execution

                switch (command) {
                    case LIST:
                        Ui.showMessage(ListCommand.execute(taskList.getAllTasks()));
                        break;

                    case MARK:
                        Ui.showMessage(Mark.execute(userInput, taskList.getAllTasks()));
                        Storage.updateList(taskList.getAllTasks());
                        break;

                    case UNMARK:
                        Ui.showMessage(Unmark.execute(userInput, taskList.getAllTasks()));
                        Storage.updateList(taskList.getAllTasks());
                        break;

                    case TODO:
                        Ui.showMessage(AddTask.todo(userInput, taskList.getAllTasks()));
                        Storage.updateList(taskList.getAllTasks());
                        break;

                    case DEADLINE:
                        Ui.showMessage(AddTask.deadline(userInput, taskList.getAllTasks()));
                        Storage.updateList(taskList.getAllTasks());
                        break;

                    case EVENT:
                        Ui.showMessage(AddTask.event(userInput, taskList.getAllTasks()));
                        Storage.updateList(taskList.getAllTasks());
                        break;

                    case DELETE:
                        Ui.showMessage(DeleteTask.execute(userInput, taskList.getAllTasks()));
                        Storage.updateList(taskList.getAllTasks());
                        break;

                    case DAYPLAN:
                        Ui.showMessage(DayPlan.execute(userInput, taskList.getAllTasks()));
                        break;

                    case EXIT:
                        Ui.showExitMessage();
                        return; // Exit the program

                    case UNKNOWN:
                    default:
                        throw new PiggyException("Unfortunately, I don't know what that means. Please try again.");
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
                    throw new PiggyException("The '" + command.toString().toLowerCase() + "' command does not take any arguments.");
                }
                break;

            case MARK:
            case UNMARK:
            case DELETE:
                if (argLength != 2) {
                    throw new PiggyException("The '" + command.toString().toLowerCase() + "' command requires exactly one task number.");
                }
                break;

            case TODO:
                if (argLength != 2) {
                    throw new PiggyException("The 'todo' command requires a task description.");
                }
                break;

            case DEADLINE:
                if (argLength < 2 || !inputParts[1].contains("/by")) {
                    throw new PiggyException("The 'deadline' command requires a task description and a due date. Format: deadline <task> /by <d/M/yyyy HHmm>");
                }
                break;

            case EVENT:
                if (argLength < 2 || !inputParts[1].contains("/from") || !inputParts[1].contains("/to")) {
                    throw new PiggyException("The 'event' command requires a task description, start date, and end date. Format: event <task> /from <d/M/yyyy HHmm> /to <d/M/yyyy HHmm>");
                }
                break;

            case DAYPLAN:
                if (argLength != 2) {
                    throw new PiggyException("The 'agenda' command requires a valid date. Format: agenda for <d/M/yyyy>");
                }
                break;

            case UNKNOWN:
            default:
                throw new PiggyException("Unknown command. Please try again.");
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