package pl.agh.cs.oop.jerzywilczek.pathfindingVisualizer.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import pl.agh.cs.oop.jerzywilczek.pathfindingVisualizer.model.map.Field;
import pl.agh.cs.oop.jerzywilczek.pathfindingVisualizer.model.map.FieldState;
import pl.agh.cs.oop.jerzywilczek.pathfindingVisualizer.model.map.FieldType;
import pl.agh.cs.oop.jerzywilczek.pathfindingVisualizer.model.map.PathfindingMap;

import java.util.List;
import java.util.Map;

public class Drawer {
    private final Canvas canvas;
    private final GraphicsContext context;
    private final PathfindingMap pathfindingMap;
    private final Map<PathfindingMap.Position, Field> fields;
    private final double fieldWidth;
    private final double fieldHeight;

    private final Color processed = Color.web("#61892F");
    private final Color startEnd = Color.web("#86C232");
    private final Color wall = Color.web("#222629");
    private final Color darkGray = Color.web("#474B4F");
    private final Color background = Color.web("#6B6E70");


    public Drawer(Canvas canvas, PathfindingMap pathfindingMap){
        this.canvas = canvas;
        this.context = canvas.getGraphicsContext2D();
        this.pathfindingMap = pathfindingMap;
        this.fields = pathfindingMap.getMap();
        fieldWidth = canvas.getWidth() /  pathfindingMap.getWidth();
        fieldHeight = canvas.getHeight() / pathfindingMap.getHeight();
    }

    public void fullUpdate(){
        pathfindingMap.getMap().forEach(this::drawField);
    }

    public void updatePositions(List<PathfindingMap.Position> positions){
        positions.forEach(this::updatePosition);
    }

    public void updatePosition(PathfindingMap.Position position){
        drawField(position, fields.get(position));
    }

    private void drawField(PathfindingMap.Position position, Paint paint){
        context.setFill(paint);
        context.fillRect(position.x * fieldWidth, position.y * fieldHeight, fieldWidth + 1, fieldHeight + 1);
    }

    private void drawField(PathfindingMap.Position position, Field field){
        drawField(position, findColor(field));
    }

    public Color findColor(Field field){
        if(field.getFieldType() == FieldType.WALL) return wall;
        if(field.getFieldState() == FieldState.PROCESSED) return processed;
        if(field.getFieldState() == FieldState.BEING_PROCESSED) return processed;
        return background;
    }
}
