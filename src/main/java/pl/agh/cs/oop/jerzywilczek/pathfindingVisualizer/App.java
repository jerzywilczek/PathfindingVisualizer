package pl.agh.cs.oop.jerzywilczek.pathfindingVisualizer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.agh.cs.oop.jerzywilczek.pathfindingVisualizer.gui.MainController;
import pl.agh.cs.oop.jerzywilczek.pathfindingVisualizer.gui.MainView;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private MainView view;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource("main.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        view = new MainView(loader.getController());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}