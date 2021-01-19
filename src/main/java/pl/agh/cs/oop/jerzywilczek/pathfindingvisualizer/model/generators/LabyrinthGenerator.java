package pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.generators;

import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.Field;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.FieldType;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.PathfindingMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class LabyrinthGenerator {
    protected final PathfindingMap pathfindingMap;
    private List<PathfindingMap.Position> walls;
    public LabyrinthGenerator(PathfindingMap pathfindingMap){
        this.pathfindingMap = pathfindingMap;
        this.walls = generateWalls();
    }

    public List<PathfindingMap.Position> getWalls(){
        return Collections.unmodifiableList(walls);
    }

    public void writeToMap(){
        Map<PathfindingMap.Position, Field> map = pathfindingMap.getMap();
        walls.forEach(position -> map.get(position).setFieldType(FieldType.WALL));
    }

    public void changeLabyrinth(){
        walls = generateWalls();
    }

    protected abstract List<PathfindingMap.Position> generateWalls();
}
