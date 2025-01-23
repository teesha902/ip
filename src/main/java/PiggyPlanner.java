import commands.*;
import exception.PiggyException;
import tasks.*;

import java.util.Scanner;
import java.util.ArrayList;

public class PiggyPlanner {
    public static void main(String[] args) {
        printToScreen("Hi! I'm your PiggyPlanner\nWhat shall we complete today?");
        ArrayList<Task> taskList = new ArrayList<>();
        Scanner reader = new Scanner(System.in);

        while (true) {
            String userInput = reader.nextLine();
            CommandType command = CommandType.fromString(userInput.split(" ")[0]); // Get the command type

            try {
                switch (command) {
                    case LIST:
                        printToScreen(ListCommand.execute(taskList));
                        break;

                    case MARK:
                        printToScreen(Mark.execute(userInput, taskList));
                        break;

                    case UNMARK:
                        printToScreen(Unmark.execute(userInput, taskList));
                        break;

                    case TODO:
                        printToScreen(AddTask.todo(userInput, taskList));
                        break;

                    case DEADLINE:
                        printToScreen(AddTask.deadline(userInput, taskList));
                        break;

                    case EVENT:
                        printToScreen(AddTask.event(userInput, taskList));
                        break;

                    case DELETE:
                        printToScreen(DeleteTask.execute(userInput, taskList));
                        break;

                    case EXIT:
                        printToScreen("Bye. Hope to see you again soon!");
                        return; // Exit the program

                    case UNKNOWN:
                    default:
                        throw new PiggyException("Unfortunately, I don't know what that means. Please try again.");
                }
            } catch (PiggyException e) {
                printToScreen(e.getMessage());
            }
        }
    }

    public static void printToScreen(String txt) {
        System.out.println("____________________________________________________________");
        System.out.println(txt);
        System.out.println("____________________________________________________________");
    }
}