package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Defines the display for the Lion in the user interface.
 */
public class Lion extends UiPart<Region> {

    private static final String FXML = "Lion.fxml";

    @FXML
    private ScrollPane messageScrollPane;

    @FXML
    private VBox responseWindow;

    public Lion () {
        super(FXML);
        messageScrollPane.setFitToHeight(false);
        messageScrollPane.vvalueProperty().bind(responseWindow.heightProperty());
    }

    public void setResponse(String response) {
        Label label = new Label(response);
        label.setPadding(new Insets(5));
        label.setWrapText(true);
        label.getStyleClass().add("label-bright");

        responseWindow.getChildren().addAll(label);
    }
}
