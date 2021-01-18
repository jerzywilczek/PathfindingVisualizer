package pl.agh.cs.oop.jerzywilczek.gui;

import java.io.IOException;
import javafx.fxml.FXML;
import pl.agh.cs.oop.jerzywilczek.App;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
