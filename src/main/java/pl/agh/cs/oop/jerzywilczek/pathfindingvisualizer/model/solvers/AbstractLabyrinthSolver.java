package pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.solvers;

import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.FieldState;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.PathfindingMap;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class AbstractLabyrinthSolver {
    protected PathfindingMap map;
    protected Map<PathfindingMap.Position, PathfindingMap.Position> parent = new HashMap<>();
    public AbstractLabyrinthSolver(PathfindingMap map){
        this.map = map;
    }

    /**
     * This method should do what is considered one step of a solving algorithm
     * @return List of Positions affected by this step
     */
    public abstract List<PathfindingMap.Position> nextStep();
    public abstract boolean finished();

    public List<PathfindingMap.Position> getSolvedPath() {
        if(!parent.containsKey(map.getEndPosition()))
            return new LinkedList<>();

        PathfindingMap.Position current = map.getEndPosition();
        map.getMap().get(current).setFieldState(FieldState.PATH);

        LinkedList<PathfindingMap.Position> result = new LinkedList<>();
        result.addFirst(current);

        while(parent.containsKey(current) && current != map.getStartPosition()){
            current = parent.get(current);
            result.addLast(current);
            map.getMap().get(current).setFieldState(FieldState.PATH);
        }

        return result;
    }
}
