package pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.generators;

import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.PathfindingMap;

import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SimpleGenerator extends AbstractLabyrinthGenerator{
    public SimpleGenerator(PathfindingMap pathfindingMap) {
        super(pathfindingMap);
    }

    @Override
    protected LinkedList<PathfindingMap.Position> generateWalls() {
        return IntStream.range(0, (int)(pathfindingMap.getWidth() * pathfindingMap.getHeight() * 0.3))
                .mapToObj(i -> pathfindingMap.new Position(random.nextInt(pathfindingMap.getWidth()), random.nextInt(pathfindingMap.getHeight())))
                .filter(position -> position != pathfindingMap.getStartPosition() && position != pathfindingMap.getEndPosition())
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
