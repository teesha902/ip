package ui;

public class Ui {
    public static void showWelcomeMessage() {
        System.out.println("Hi! I'm your PiggyPlanner\nWhat shall we get done today?");
    }

    public static void showExitMessage() {
        System.out.println("Bye. Excited to work with you again soon!");
    }

    public static void showMessage(String message) {
        System.out.println("____________________________________________________________");
        System.out.println(message);
        System.out.println("____________________________________________________________");
    }
}