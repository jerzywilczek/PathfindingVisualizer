package pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.gui.animation.AnimationFinishedObserver;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.gui.animation.Animator;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.gui.animation.Drawer;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.generators.AbstractLabyrinthGenerator;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.generators.RecursiveDivisionGenerator;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.generators.SimpleGenerator;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.PathfindingMap;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.solvers.AbstractLabyrinthSolver;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.solvers.BFSSolver;

import java.util.List;

public class MainController implements AnimationFinishedObserver {

    private PathfindingMap map;
    private Drawer drawer;
    private List<Control> controls;
    private Animator animator;
    private boolean animatingMode = false;

    @FXML
    private ChoiceBox<GeneratorMenuItem> generatorChoiceBox;

    @FXML
    private ChoiceBox<SolverMenuItem> solverChoiceBox;

    @FXML
    private Canvas canvas;

    @FXML
    private Button generateButton;

    @FXML
    private void generate(ActionEvent event) {
        map.clear();
        drawer.fullUpdate();
        AbstractLabyrinthGenerator generator = switch (generatorChoiceBox.getValue()) {
            case RECURSIVE -> new RecursiveDivisionGenerator(map);
            case SIMPLE -> new SimpleGenerator(map);
        };
        setAnimatingMode(true);
        animator.animateBatch(generator.getWalls());
    }

    @FXML
    private Button solveButton;

    @FXML
    private void solve(ActionEvent event) {
        AbstractLabyrinthSolver solver = switch (solverChoiceBox.getValue()) {
            case BFS -> new BFSSolver(map);
        };
        setAnimatingMode(true);
        animator.animateSolving(solver);
    }

    @FXML
    private Button skipButton;

    @FXML
    private void skip(ActionEvent event) {
        if (animator.isAnimating()) {
            animator.skip();
        }
    }

    @FXML
    private Button animationToggleButton;

    @FXML
    private void animationToggle(ActionEvent event) {
        animator.toggleRunning();
    }

    @FXML
    private Button clearButton;

    @FXML
    private void clearMap(ActionEvent event){
        map.clear();
        drawer.fullUpdate();
    }

    @FXML
    private void initialize() {
        map = new PathfindingMap(50, 50);
        drawer = new Drawer(canvas, map);
        animator = new Animator(drawer);
        animator.addObserver(this);

        generatorChoiceBox.getItems().addAll(GeneratorMenuItem.values());
        generatorChoiceBox.setValue(GeneratorMenuItem.values()[0]);
        solverChoiceBox.getItems().addAll(SolverMenuItem.values());
        solverChoiceBox.setValue(SolverMenuItem.values()[0]);

        controls = List.of(
                generatorChoiceBox,
                generateButton,
                solverChoiceBox,
                solveButton,
                skipButton,
                animationToggleButton,
                clearButton
        );
    }

    private void setAnimatingMode(boolean value) {
        controls.forEach(control -> control.setDisable(value));
        skipButton.setDisable(!value);
        animationToggleButton.setDisable(!value);
        animatingMode = value;
    }

    @Override
    public void animationFinished() {
        setAnimatingMode(false);
    }
}
