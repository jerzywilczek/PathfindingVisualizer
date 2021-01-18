package pl.agh.cs.oop.jerzywilczek;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.agh.cs.oop.jerzywilczek.gui.MainController;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource("main.fxml"));
        scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}