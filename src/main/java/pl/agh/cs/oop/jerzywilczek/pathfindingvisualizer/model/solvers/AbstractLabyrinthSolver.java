package pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.solvers;

import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.PathfindingMap;

import java.util.List;

public abstract class AbstractLabyrinthSolver {
    private PathfindingMap map;
    public AbstractLabyrinthSolver(PathfindingMap map){
        this.map = map;
    }

    /**
     * This method should do what is considered one step of a solving algorithm
     * @return List of Positions affected by this step
     */
    public abstract List<PathfindingMap.Position> nextStep();
    public abstract boolean finished();
    public abstract List<PathfindingMap.Position> getSolvedPath();
}
