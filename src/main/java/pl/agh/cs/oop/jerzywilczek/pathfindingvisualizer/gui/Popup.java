package pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Popup {
    @FXML
    private Text text;

    public void setText(String message){
        text.setText(message);
    }

    @FXML
    private void okPressed(ActionEvent event){
        ((Stage) text.getScene().getWindow()).close();
    }
}
