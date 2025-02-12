package piggyplanner;

import javafx.application.Application;

/**
 * This class serves as the entry point to the PiggyPlanner application.
 * It is responsible for launching the JavaFX graphical user interface (GUI).
 */
public class Launcher {
    /**
     * The main method that starts the PiggyPlanner application.
     * This method delegates to JavaFX to initialize the UI.
     *
     * @param args Command-line arguments passed to the application (not used).
     */
    public static void main(String[] args) { //entry point to Java app
        Application.launch(ui.MainWindow.class, args); //tells JavaFX to start GUI by launching MainWindow
    }
}
