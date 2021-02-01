package pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.gui;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.generators.AbstractLabyrinthGenerator;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.generators.RecursiveDivisionGenerator;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.PathfindingMap;

import java.util.LinkedList;
import java.util.List;

public class MainController {

    private PathfindingMap map;
    private Drawer drawer;
    private List<Control> controls;

    private class GenerationAnimator extends AnimationTimer{
        List<PathfindingMap.Position> walls;
        public GenerationAnimator(List<PathfindingMap.Position> walls){
            this.walls = walls;
            setControlsDisable(true);
        }

        @Override
        public void handle(long now) {
            if(walls.isEmpty()){
                setControlsDisable(false);
                this.stop();
                return;
            }
            drawer.updatePosition(walls.remove(0));
        }
    }

    @FXML
    private Canvas canvas;

    @FXML
    private ChoiceBox<GeneratorMenuItem> generatorChoiceBox;

    @FXML
    private Button generateButton;

    @FXML
    private void initialize(){
        this.map = new PathfindingMap(50, 50);
        this.drawer = new Drawer(canvas, map);
        drawer.fullUpdate();
        generatorChoiceBox.getItems().addAll(GeneratorMenuItem.values());
        generatorChoiceBox.setValue(GeneratorMenuItem.RECURSIVE);
        controls = List.of(
                generatorChoiceBox,
                generateButton
        );
    }

    private void setControlsDisable(boolean value){
        controls.forEach(control -> control.setDisable(value));
    }

    @FXML
    private void generate(ActionEvent event){
        map.clear();
        drawer.fullUpdate();
        AbstractLabyrinthGenerator generator = switch (generatorChoiceBox.getValue()) {
            case RECURSIVE -> new RecursiveDivisionGenerator(map);
            case STH_ELSE -> new AbstractLabyrinthGenerator(map) {
                @Override
                protected LinkedList<PathfindingMap.Position> generateWalls() {
                    return new LinkedList<>(pathfindingMap.getMap().keySet());
                }
            };
        };
        new GenerationAnimator(generator.getWalls()).start();
    }
}
