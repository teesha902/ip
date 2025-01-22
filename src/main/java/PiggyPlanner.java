import java.util.Scanner;

public class PiggyPlanner {
    public static void main(String[] args) {
        printHorizontalLine();
        System.out.println(" Hello! I'm you PiggyPlanner");
        System.out.println(" What shall we do today?");
        printHorizontalLine();

        Scanner reader = new Scanner(System.in);
        while (true) {
            String userInput = reader.nextLine();
            if (userInput.equals("bye")) {
                break;
            }
            printHorizontalLine();
            System.out.println(userInput);
            printHorizontalLine();
        }

        // Exit message
        printHorizontalLine();
        System.out.println(" Bye. Hope to see you again soon!");
        printHorizontalLine();
    }

    private static void printHorizontalLine() {
        System.out.println("____________________________________________________________");
    }
}





