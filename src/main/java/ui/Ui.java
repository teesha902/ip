package ui;

/**
 * Handles user interface interactions by displaying messages.
 */
public class Ui {
    /**
     * Displays the welcome message when the application starts.
     */
    public static String showWelcomeMessage() {
        System.out.println("Hi! I'm your PiggyPlanner\nWhat shall we get done today?");
        return "Hi! I'm your PiggyPlanner\n\nWhat shall we get done today?";
    }

    /**
     * Displays the exit message when the application is terminated.
     */
    public static String showExitMessage() {
        System.out.println("Bye. Excited to work with you again soon!");
        return "Bye. Excited to work with you again soon!";
    }

    /**
     * Displays a given message within a formatted separator.
     *
     * @param message The message to be displayed.
     */
    public static void showMessage(String message) {
        System.out.println("____________________________________________________________");
        System.out.println(message);
        System.out.println("____________________________________________________________");
    }
}
