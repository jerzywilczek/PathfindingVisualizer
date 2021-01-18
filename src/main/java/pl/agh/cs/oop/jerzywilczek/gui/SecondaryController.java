package pl.agh.cs.oop.jerzywilczek.gui;

import java.io.IOException;
import javafx.fxml.FXML;
import pl.agh.cs.oop.jerzywilczek.App;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}