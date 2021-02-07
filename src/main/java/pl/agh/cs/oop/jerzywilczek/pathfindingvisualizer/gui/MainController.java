package pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.gui.animation.AnimationFinishedObserver;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.gui.animation.Animator;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.gui.animation.Drawer;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.generators.AbstractLabyrinthGenerator;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.generators.RecursiveDivisionGenerator;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.generators.SimpleGenerator;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.Field;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.FieldType;
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
    private void canvasClicked(MouseEvent event) {
        if (animatingMode)
            return;
        double fieldWidth = canvas.getWidth() / map.getWidth(), fieldHeight = canvas.getHeight() / map.getHeight();
        int x = (int) (event.getX() / fieldWidth), y = (int) (event.getY() / fieldHeight);
        if (x != 0 &&
            x != map.getWidth() - 1 &&
            y != 0 &&
            y != map.getHeight() - 1
        ) {
            PathfindingMap.Position position = map.new Position(x, y);

            if (!position.equals(map.getStartPosition()) && !position.equals(map.getEndPosition())) {
                Field field = map.getMap().get(position);

                switch (field.getFieldType()) {
                    case EMPTY -> field.setFieldType(FieldType.WALL);
                    case WALL -> field.setFieldType(FieldType.EMPTY);
                }

                drawer.fullUpdate();
            }
        }
    }

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
    private void clearMap(ActionEvent event) {
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

    @FXML
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case A -> map.offsetStart(-1, 0);
            case W -> map.offsetStart(0, -1);
            case D -> map.offsetStart(1, 0);
            case S -> map.offsetStart(0, 1);
            default -> {
            }
        }
        if (map.getStartPosition().equals(map.getEndPosition())) {
            map.clear();
        }
        drawer.fullUpdate();
    }
}
