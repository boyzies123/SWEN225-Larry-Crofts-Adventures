package nz.ac.wgtn.swen225.lc.app;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

/**
 * The class for displaying information in the game. 
 * Called through the static display method.
 * @author Harry Booth-Beach 300614975
 *
 */
public class Information {
  /**
   * Display information in the form of a pop up message
   * @param title Title of the pop up message
   * @param message Message that will be displayed
   */
  public static void display(String title, String message) {
    Stage window = new Stage();
    
    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle(title);
    window.setMinWidth(250);
    
    Label label = new Label();
    label.setText(message);
    Button closeButton = new Button("Done!");
    closeButton.setOnAction(e -> window.close());
    
    VBox layout = new VBox(10);
    layout.getChildren().addAll(label, closeButton);
    layout.setAlignment(Pos.CENTER);
    
    Scene scene = new Scene(layout);
    window.setScene(scene);
    window.showAndWait();
  }
}