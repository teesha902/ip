package ui;

import java.io.IOException;
//import javafx.scene.control.Button;

import exception.PiggyException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import piggyplanner.PiggyPlanner;

public class MainWindow extends Application {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    //@FXML
    //private Button sendButton;

    private PiggyPlanner piggyPlanner;

    @Override
    //overriding start method from Application
    public void start(Stage stage) throws PiggyException { //main method JavaFX calls to set up + display GUI window
        //PiggyPlanner piggyPlanner = new PiggyPlanner(); //Connects to your task manager (backend logic)
        this.piggyPlanner = new PiggyPlanner();

        try {
            // Load FXML file for UI layout - read by fxmlloader
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
            //attach anchor pane to window (Stage) using Scene.
            AnchorPane ap = fxmlLoader.load(); //Load UI components into AnchorPane
            MainWindow controller = fxmlLoader.getController();
            controller.setPiggyPlanner(this.piggyPlanner); // Pass initialized piggyPlanner to controller
            Scene scene = new Scene(ap); //scene = window content

            // Set up the stage (window)
            stage.setTitle("PiggyPlanner 🐷");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        // Auto-scroll as new messages appear
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
        // Add key event listener for Enter key
        userInput.setOnAction(event -> handleUserInput());
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText(); // Get user's input from text field
        String response = piggyPlanner.getResponse(input); // Get response from PiggyPlanner logic

        // Display user input and PiggyPlanner's response in the dialogContainer
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog("You: " + input),
                DialogBox.getPiggyPlannerDialog("🐷: " + response)
        );

        userInput.clear(); // Clear input field after sending msg
    }

    public void setPiggyPlanner(PiggyPlanner piggyPlanner) {
        this.piggyPlanner = piggyPlanner;
    }
}
