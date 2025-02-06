package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

// DialogBox = message bubble for user/piggy, inherits from Hbox
public class DialogBox extends HBox {
    private final Label text;
    private final ImageView displayPicture;

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
        Circle clip = new Circle(25, 21, 14); // x, y, radius
        displayPicture.setClip(clip); // Apply circular mask

        this.setAlignment(Pos.TOP_RIGHT); // Default alignment for user
        this.setSpacing(10);
        this.setMaxWidth(Double.MAX_VALUE); //Allow the dialog box to take full width

        this.getChildren().addAll(text, displayPicture); // Add message and image
    }
    // Flips the dialog box alignment to the left for PiggyPlanner responses
    private void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        //Converts curr elements (text, images) in dialog box into list to manipulate
        ObservableList<Node> nodes = FXCollections.observableArrayList(this.getChildren());
        //reverses the order of elements
        FXCollections.reverse(nodes);
        //update dialog box
        this.getChildren().setAll(nodes);
    }

    // Factory method for user dialog
    public static DialogBox getUserDialog(String message, Image userImage) {
        //Image userImage = new Image(DialogBox.class.getResourceAsStream("/images/user.png"));
        DialogBox dialogBox = new DialogBox(message, userImage);
        dialogBox.setAlignment(Pos.TOP_RIGHT); //Ensure alignment to the right
        return dialogBox;
    }

    // Factory method for PiggyPlanner dialog (with flip)
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
