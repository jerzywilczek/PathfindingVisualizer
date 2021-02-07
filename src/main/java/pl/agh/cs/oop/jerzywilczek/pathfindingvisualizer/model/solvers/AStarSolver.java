package pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.solvers;

import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.Field;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.FieldState;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.FieldType;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.PathfindingMap;

import java.util.*;
import java.util.stream.IntStream;

import static java.lang.Math.abs;

public class AStarSolver extends AbstractLabyrinthSolver{
    private boolean finished = false;
    private final Map<PathfindingMap.Position, Double> shortestPath;
    private final PriorityQueue<SortingEntry> queue = new PriorityQueue<>();

    public AStarSolver(PathfindingMap map) {
        super(map);

        shortestPath = new HashMap<>();
        for(int i = 0; i < map.getWidth(); i++){
            for(int j = 0; j < map.getHeight(); j++){
                PathfindingMap.Position position = map.new Position(i, j);
                shortestPath.put(position, Double.MAX_VALUE);
            }
        }

        shortestPath.put(map.getStartPosition(), (double) 0);
        double startEstimate = estimate(map.getStartPosition());
        queue.add(new SortingEntry(map.getStartPosition(), startEstimate));
    }

    @Override
    public List<PathfindingMap.Position> nextStep() {
        LinkedList<PathfindingMap.Position> result = new LinkedList<>();

        if(queue.isEmpty() || finished){
            finished = true;
            return result;
        }

        PathfindingMap.Position current = queue.poll().position;
        map.getMap().get(current).setFieldState(FieldState.PROCESSED);
        result.add(current);

        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                if(abs(i+j) == 1){
                    PathfindingMap.Position child = map.new Position(current.x + i, current.y + j);

                    Field field = map.getMap().get(child);
                    if((field.getFieldState() == FieldState.UNPROCESSED) && (field.getFieldType() != FieldType.WALL)){
                        double path = 1 + shortestPath.get(current);
                        if(path < shortestPath.get(child)){
                            queue.add(new SortingEntry(child, path + estimate(child)));
                            field.setFieldState(FieldState.BEING_PROCESSED);
                            parent.put(child, current);
                            shortestPath.put(child, path);
                            result.add(child);
                            if(child.equals(map.getEndPosition())){
                                finished = true;
                                return result;
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public boolean finished() {
        return finished;
    }

    private double estimate(PathfindingMap.Position position){
        double xDistance = position.x - map.getEndPosition().x;
        double yDistance = position.y - map.getEndPosition().y;

        return Math.abs(xDistance) + Math.abs(yDistance);
    }

    private static class SortingEntry implements Comparable<SortingEntry>{
        public final PathfindingMap.Position position;
        public final double distance;

        public SortingEntry(PathfindingMap.Position position, double distance) {
            this.position = position;
            this.distance = distance;
        }

        @Override
        public int compareTo(SortingEntry other) {
            return Double.compare(this.distance, other.distance);
        }
    }
}
