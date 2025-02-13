package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;


/**
 * DialogBox represents a message bubble for the user and PiggyPlanner.
 * It extends HBox and allows messages to be displayed with an image.
 */
public class DialogBox extends HBox {
    private final Label text;
    private final ImageView displayPicture;

    /**
     * Constructs a DialogBox with a specified message and image.
     *
     * @param message The text to display.
     * @param img The image to display.
     */
    public DialogBox(String message, Image img) {
        text = new Label(message);
        text.setWrapText(true); //text wrapping
        text.setMaxWidth(280); //limit width - prevent overflow
        displayPicture = new ImageView(img);

        // Set size and style
        displayPicture.setFitWidth(50);
        displayPicture.setFitHeight(50);
        displayPicture.setPreserveRatio(true);
        text.setStyle("-fx-background-color: #ffccd5; -fx-padding: 10; -fx-border-radius: 10; "
                + "-fx-background-radius: 10;");

        // Create a circular clip for the image
        Circle clip = new Circle(25, 21, 13.5); // x, y, radius
        displayPicture.setClip(clip); // Apply circular mask

        this.setAlignment(Pos.TOP_RIGHT); // Default alignment for user
        this.setSpacing(2);
        this.setMaxWidth(Double.MAX_VALUE); //Allow the dialog box to take full width

        this.getChildren().addAll(text, displayPicture); // Add message and image
    }
    /**
     * Flips the dialog box alignment to the left, for PiggyPlanner responses.
     */
    private void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        //Converts curr elements (text, images) in dialog box into list to manipulate
        ObservableList<Node> nodes = FXCollections.observableArrayList(this.getChildren());
        //reverses the order of elements
        FXCollections.reverse(nodes);
        //update dialog box
        this.getChildren().setAll(nodes);
    }

    /**
     * Creates a DialogBox for user messages.
     *
     * @param message The user’s message.
     * @param userImage The user’s profile image.
     * @return A DialogBox aligned to the right.
     */
    public static DialogBox getUserDialog(String message, Image userImage) {
        //Image userImage = new Image(DialogBox.class.getResourceAsStream("/images/user.png"));
        DialogBox dialogBox = new DialogBox(message, userImage);
        dialogBox.setAlignment(Pos.TOP_RIGHT); //Ensure alignment to the right
        return dialogBox;
    }

    /**
     * Creates a DialogBox for PiggyPlanner responses, with flipped alignment.
     *
     * @param message The PiggyPlanner’s message.
     * @param piggyImage The PiggyPlanner’s profile image.
     * @return A DialogBox aligned to the left.
     */
    public static DialogBox getPiggyPlannerDialog(String message, Image piggyImage) {
        //Image pigImage = new Image(DialogBox.class.getResourceAsStream("/images/pig.png"));
        DialogBox dialogBox = new DialogBox(message, piggyImage);
        dialogBox.flip();
        //lighter pink for piggy responses
        dialogBox.text.setStyle("-fx-background-color: #ffe5ec; -fx-padding: 10; -fx-border-radius: 10; "
                + "-fx-background-radius: 10;");
        return dialogBox;
    }
}
