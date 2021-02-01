package pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.generators;

import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.Field;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.FieldType;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.PathfindingMap;

import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

public abstract class AbstractLabyrinthGenerator {
    protected final Random random = new Random();
    protected final PathfindingMap pathfindingMap;
    private LinkedList<PathfindingMap.Position> walls;
    public AbstractLabyrinthGenerator(PathfindingMap pathfindingMap){
        this.pathfindingMap = pathfindingMap;
        walls = generateWalls();
        writeToMap();
    }

    public LinkedList<PathfindingMap.Position> getWalls(){
        return new LinkedList<>(walls);
    }

    public void writeToMap(){
        pathfindingMap.clear();
        Map<PathfindingMap.Position, Field> map = pathfindingMap.getMap();
        walls.forEach(position -> map.get(position).setFieldType(FieldType.WALL));
    }

    public void changeLabyrinth(){
        walls = generateWalls();
        writeToMap();
    }

    protected abstract LinkedList<PathfindingMap.Position> generateWalls();
}
