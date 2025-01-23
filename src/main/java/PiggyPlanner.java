import commands.*;
import exception.PiggyException;
import tasks.*;
//import tasks.Task;

import java.util.Scanner;
import java.util.ArrayList;

public class PiggyPlanner {
    public static void main(String[] args) {
        printToScreen("Hi! I'm your PiggyPlanner\nWhat shall we complete today?");
        ArrayList<Task> taskList = new ArrayList<>();
        Scanner reader = new Scanner(System.in);

        while (true) {
            String userInput = reader.nextLine();

            try {
                if (userInput.equals("bye")) {
                    break;
                } else if (userInput.equals("list")) {
                    printToScreen(ListCommand.execute(taskList));

                } else if (userInput.startsWith("mark")) {
                    printToScreen(Mark.execute(userInput, taskList));
                } else if (userInput.startsWith("unmark")) {
                    printToScreen(Unmark.execute(userInput, taskList));

                } else if (userInput.startsWith("todo")) {
                    printToScreen(AddTask.todo(userInput, taskList));
                } else if (userInput.startsWith("deadline")) {
                    printToScreen(AddTask.deadline(userInput, taskList));
                } else if (userInput.startsWith("event")) {
                    printToScreen(AddTask.event(userInput, taskList));

                } else {
                    throw new PiggyException("Unfortunately, I don't know what that means. Please try again.");
                }
            } catch (PiggyException e) {
                printToScreen(e.getMessage());
            }
        }

        System.out.println("Bye. Hope to see you again soon!");
    }

    public static void printToScreen(String txt) {
        System.out.println("____________________________________________________________");
        System.out.println(txt);
        System.out.println("____________________________________________________________");
    }
}