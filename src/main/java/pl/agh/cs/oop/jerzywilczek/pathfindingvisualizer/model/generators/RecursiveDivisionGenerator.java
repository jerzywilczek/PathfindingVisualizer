package pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.generators;

import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.PathfindingMap;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RecursiveDivisionGenerator extends AbstractLabyrinthGenerator {
    private enum Axis {
        X,
        Y;
        
        public Axis other(){
            return values()[1 - this.ordinal()];
        }
    }

    public RecursiveDivisionGenerator(PathfindingMap pathfindingMap) {
        super(pathfindingMap);
    }
    
    private PathfindingMap.Position withAxis(int choiceValue, int otherValue, Axis choiceAxis){
        int[] pos = new int[2];
        pos[choiceAxis.ordinal()] = choiceValue;
        pos[choiceAxis.other().ordinal()] = otherValue;
        return pathfindingMap.new Position(pos);
    }
    
    private LinkedList<PathfindingMap.Position> findSplits(PathfindingMap.Position lowerLeft, PathfindingMap.Position upperRight, Set<PathfindingMap.Position> doors, Axis axis, boolean parentDegenerate) {
        int choiceAxis = axis.ordinal();
        int otherAxis = axis.other().ordinal();
        int[] leftTab = lowerLeft.toArray(), rightTab = upperRight.toArray();

        int totalPossibilities = rightTab[choiceAxis] - leftTab[choiceAxis] - 3;
        if(totalPossibilities <= 2){
            long takenPossibilities = IntStream.range(leftTab[choiceAxis] + 2, rightTab[choiceAxis] - 1)
                    .filter(x -> doors.contains(withAxis(x, leftTab[otherAxis], axis)) || doors.contains(withAxis(x, rightTab[otherAxis], axis)))
                    .count();

            if(totalPossibilities - takenPossibilities <= 0){
                if(parentDegenerate)
                    return new LinkedList<>();
                else
                    return findSplits(lowerLeft, upperRight, doors, axis.other(), true);
            }
        }

        int division;
        do {
            division = leftTab[choiceAxis] + random.nextInt(rightTab[choiceAxis] - leftTab[choiceAxis] - 3) + 2;
        } while (doors.contains(withAxis(division, leftTab[otherAxis], axis)) || doors.contains(withAxis(division, rightTab[otherAxis], axis)));

        int door = leftTab[otherAxis] + random.nextInt(rightTab[otherAxis] - leftTab[otherAxis] - 1) + 1;
        doors.add(withAxis(division, door, axis));

        int finalDivision = division; // idk why but it won't compile without this
        LinkedList<PathfindingMap.Position> walls = IntStream.range(leftTab[otherAxis] + 1, rightTab[otherAxis])
                .filter(height -> height != door)
                .mapToObj(height -> withAxis(finalDivision, height, axis))
                .collect(Collectors.toCollection(LinkedList::new));

        walls.addAll(findSplits(lowerLeft, withAxis(division, rightTab[otherAxis], axis), doors, axis.other(), false));
        walls.addAll(findSplits(withAxis(division, leftTab[otherAxis], axis), upperRight, doors, axis.other(), false));
        return walls;
    }

    @Override
    protected LinkedList<PathfindingMap.Position> generateWalls() {
        return findSplits(
                pathfindingMap.new Position(0, 0),
                pathfindingMap.new Position(pathfindingMap.getWidth() - 1, pathfindingMap.getHeight() - 1),
                new HashSet<>(),
                Axis.X,
                false
        );
    }
}
