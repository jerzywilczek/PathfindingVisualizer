package pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.generators;

import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.PathfindingMap;

import java.util.LinkedList;
import java.util.List;

public class RecursiveDivisionGenerator extends AbstractLabyrinthGenerator {
    public RecursiveDivisionGenerator(PathfindingMap pathfindingMap) {
        super(pathfindingMap);
    }

    @Override
    protected LinkedList<PathfindingMap.Position> generateWalls() {
        return new LinkedList<>(List.of(pathfindingMap.new Position(pathfindingMap.getWidth() / 2, pathfindingMap.getHeight() / 2)));
    }
}
