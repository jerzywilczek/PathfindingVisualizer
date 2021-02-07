package pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.gui.animation;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.Field;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.FieldType;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.PathfindingMap;

import java.util.List;
import java.util.Map;

public class Drawer {
    private final Canvas canvas;
    private final GraphicsContext context;
    private final PathfindingMap pathfindingMap;
    private final Map<PathfindingMap.Position, Field> fields;
    private final double fieldWidth;
    private final double fieldHeight;

    private final Color start = Color.web("#FAED26");
    private final Color end = Color.web("#F79E02");
    private final Color darkGreen =  Color.web("#61892F");
    private final Color beingProcessed = Color.web("#86C232");
    private final Color processed = Color.web("#53900F");
    private final Color wall = Color.web("#222629");
    private final Color darkGray = Color.web("#474B4F");
    private final Color background = Color.web("#6B6E70");
    private final Color path = Color.web("#10E7DC");


    public Drawer(Canvas canvas, PathfindingMap pathfindingMap) {
        this.canvas = canvas;
        this.pathfindingMap = pathfindingMap;
        context = canvas.getGraphicsContext2D();
        fields = pathfindingMap.getMap();
        fieldWidth = canvas.getWidth() /  pathfindingMap.getWidth();
        fieldHeight = canvas.getHeight() / pathfindingMap.getHeight();
        fullUpdate();
    }

    public void fullUpdate() {
        pathfindingMap.getMap().forEach(this::drawField);
        drawField(pathfindingMap.getStartPosition(), start);
        drawField(pathfindingMap.getEndPosition(), end);
    }

    public void updatePositions(List<PathfindingMap.Position> positions) {
        positions.forEach(this::updatePosition);
    }

    public void updatePosition(PathfindingMap.Position position) {
        if(position.equals(pathfindingMap.getStartPosition()))
            drawField(position, start);
        else if(position.equals(pathfindingMap.getEndPosition()))
            drawField(position, end);
        else
            drawField(position, fields.get(position));
    }

    private void drawField(PathfindingMap.Position position, Paint paint) {
        context.setFill(paint);
        context.fillRect(position.x * fieldWidth, position.y * fieldHeight, fieldWidth + 1, fieldHeight + 1);
    }

    private void drawField(PathfindingMap.Position position, Field field) {
        drawField(position, findColor(field));
    }

    public Color findColor(Field field) {
        if (field.getFieldType() == FieldType.WALL) return wall;
        return switch(field.getFieldState()){
            case PATH -> path;
            case PROCESSED -> processed;
            case BEING_PROCESSED -> beingProcessed;
            case UNPROCESSED -> background;
        };
    }
}
