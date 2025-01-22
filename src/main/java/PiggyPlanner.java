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
                                .append(taskList.get(i)).append("\n");
                                //.append(". [").append(taskList.get(i).status()).append("] ")
                                //.append(taskList.get(i)).append("\n");
                    }
                    printToScreen(listOutput.toString().trim()); // Print all tasks at once
                }

            } else if (userInput.startsWith("mark")) {
                int taskIndex = Integer.parseInt(userInput.split(" ")[1]) - 1; // Extract task index
                taskList.get(taskIndex).mark();
                printToScreen(" Good work! Let's keep going." +
                        "\n I've marked this task as done: \n  [X] " + taskList.get(taskIndex));
            } else if (userInput.startsWith("unmark")) {
                int taskIndex = Integer.parseInt(userInput.split(" ")[1]) - 1; // Extract task index
                taskList.get(taskIndex).unmark();
                printToScreen(" Oops, no problem." +
                        "\n I've unmarked the task: \n  [ ] " + taskList.get(taskIndex));

            } else if (userInput.startsWith("todo")) {
                String taskAction = userInput.substring(5).trim();
                Task newTask = new ToDo(taskAction);
                taskList.add(newTask);
                printTaskAdded(newTask, taskList.size());
            } else if (userInput.startsWith("deadline")) {
                int dueDateIndex = userInput.indexOf("/by");
                String taskAction = userInput.substring(9, dueDateIndex).trim();
                String dueDate = userInput.substring(dueDateIndex + 3).trim(); // +3 to skip "/by"
                Task newTask = new Deadline(taskAction, dueDate);
                taskList.add(newTask);
                printTaskAdded(newTask, taskList.size());
            } else if (userInput.startsWith("event")) {
                int fromIndex = userInput.indexOf("/from");
                int toIndex = userInput.indexOf("/to");
                String taskAction = userInput.substring(6, fromIndex).trim();
                String from = userInput.substring(fromIndex + 5, toIndex).trim(); // +5 to skip "/from"
                String to = userInput.substring(toIndex + 3).trim(); // +3 to skip "/to"
                Task newTask = new Event(taskAction, from, to);
                taskList.add(newTask);
                printTaskAdded(newTask, taskList.size());

            } else {
                printToScreen("I'm sorry, I don't understand that command :(");
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

    private static void printTaskAdded(Task task, int totalTasks) {
        printToScreen("New task! I've added it to the list :) \n  " + task +
                "\nNow we have a total of " + totalTasks + " things to do.");
        //need to adjust print for when task = 1 or 0
    }
}





