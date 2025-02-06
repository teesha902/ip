package piggyplanner;

import javafx.application.Application;

public class Launcher {
    public static void main(String[] args) { //entry point to Java app
        Application.launch(ui.MainWindow.class, args); //tells JavaFX to start GUI by launching MainWindow
    }
}
