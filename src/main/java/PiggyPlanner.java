import java.util.Scanner;
import java.util.ArrayList;

public class PiggyPlanner {
    public static void main(String[] args) {
        printToScreen(" Hello! I'm you PiggyPlanner \n What shall we do today?");

        ArrayList<Task> taskList = new ArrayList<>();
        Scanner reader = new Scanner(System.in);
        while (true) {
            String userInput = reader.nextLine();

            if (userInput.equals("bye")) {
                break;
            } else if (userInput.equals("list")) {
                if (taskList.isEmpty()) {
                    printToScreen("No tasks yet!");
                } else {
                    StringBuilder listOutput = new StringBuilder();
                    listOutput.append("To-do list: \n");
                    for (int i = 0; i < taskList.size(); i++) {
                        listOutput.append((i + 1))
                                .append(". [").append(taskList.get(i).status()).append("] ")
                                .append(taskList.get(i)).append("\n");
                    }
                    printToScreen(listOutput.toString().trim()); // Print all tasks at once
                }
            } else if (userInput.startsWith("mark")) {
                int taskIndex = Integer.parseInt(userInput.split(" ")[1]) - 1; // Extract task index
                taskList.get(taskIndex).mark();
                printToScreen(" Good work! Let's continue working." +
                        "\n I've marked this task as done: \n  [X] " + taskList.get(taskIndex));
            } else if (userInput.startsWith("unmark")) {
                int taskIndex = Integer.parseInt(userInput.split(" ")[1]) - 1; // Extract task index
                taskList.get(taskIndex).unmark();
                printToScreen(" Oops, no problem." +
                        "\n I've unmarked the task: \n  [ ] " + taskList.get(taskIndex));
            } else {
                Task newTask = new Task(userInput);
                taskList.add(newTask);
                printToScreen(" Added to list: " + userInput);
            }
        }

        // Exit message
        printToScreen(" Bye. Hope to see you again soon!");
    }

    private static void printToScreen(String txt) {
        System.out.println("____________________________________________________________");
        System.out.println(txt);
        System.out.println("____________________________________________________________");
    }
}





