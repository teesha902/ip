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

public class PiggyPlanner {
    private final TaskList taskList;
    private final Scanner reader;

    public PiggyPlanner() throws PiggyException {
        this.taskList = new TaskList(Storage.loadList());
        this.reader = new Scanner(System.in);
    }

    public void run() {
        Ui.showWelcomeMessage();

        while (true) {
            String userInput = reader.nextLine();
            CommandType command = CommandType.fromString(userInput.split(" ")[0]); // Get the command type

            try {
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

    public static void main(String[] args) {
        try {
            new PiggyPlanner().run();
        } catch (PiggyException e) {
            System.out.println("An error occurred while starting the program: " + e.getMessage());
        }
    }
}