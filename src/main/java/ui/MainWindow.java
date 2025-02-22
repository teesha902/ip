package ui;

import java.io.IOException;

import exception.PiggyException;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import piggyplanner.PiggyPlanner;
/**
 * MainWindow initializes and manages the graphical user interface (GUI) for PiggyPlanner.
 * It extends the JavaFX Application class and handles user interactions.
 */
public class MainWindow extends Application {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private final Image piggyImage = new Image(this.getClass().getResourceAsStream("/images/pig.png"));

    private PiggyPlanner piggyPlanner;

    /**
     * Starts the JavaFX application, setting up the primary stage and loading the UI components.
     *
     * @param stage The primary stage for the application.
     * @throws PiggyException If there is an error initializing PiggyPlanner.
     */
    @Override
    //overriding start method from Application
    public void start(Stage stage) throws PiggyException { //main method JavaFX calls to set up + display GUI window
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

            //Set  initial window size
            stage.setWidth(400);
            stage.setHeight(600);

            // define minimum window size when resizing
            stage.setMinWidth(400);
            stage.setMinHeight(600);
            //makes main UI (AnchorPane) always stretch to match scene width/height
            ap.prefWidthProperty().bind(scene.widthProperty());
            ap.prefHeightProperty().bind(scene.heightProperty());

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the UI components and sets up event listeners.
     */
    @FXML
    public void initialize() {
        // Make sure ScrollPane resizes with window
        scrollPane.fitToWidthProperty().set(true);

        // Auto-scroll as new messages appear
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
        dialogContainer.getChildren().add(DialogBox.getPiggyPlannerDialog(Ui.showWelcomeMessage(), piggyImage));

        // Ensure user input field expands properly
        AnchorPane.setLeftAnchor(userInput, 10.0);
        AnchorPane.setRightAnchor(userInput, 80.0);
        AnchorPane.setBottomAnchor(userInput, 10.0);

        // Ensure send button stays at the right
        AnchorPane.setRightAnchor(sendButton, 10.0);
        AnchorPane.setBottomAnchor(sendButton, 10.0);

        // Add key event listener for Enter key
        userInput.setOnAction(event -> handleUserInput());
    }

    /**
     * Handles user input and generates a response from PiggyPlanner.
     * Displays the conversation in the UI.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText(); // Get user's input from text field
        String response = piggyPlanner.getResponse(input); // Get response from PiggyPlanner logic

        // Display user input and PiggyPlanner's response in the dialogContainer
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getPiggyPlannerDialog(response, piggyImage)
        );
        userInput.clear(); // Clear input field after sending msg

        if (input.equalsIgnoreCase("bye")) {
            // Delay closing the window for 1.5 seconds
            PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
    }

    /**
     * Sets the PiggyPlanner instance for the UI.
     *
     * @param piggyPlanner The PiggyPlanner instance.
     */
    public void setPiggyPlanner(PiggyPlanner piggyPlanner) {
        this.piggyPlanner = piggyPlanner;
    }
}
