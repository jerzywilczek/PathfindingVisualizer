package pl.agh.cs.oop.jerzywilczek.model.solvers;

import pl.agh.cs.oop.jerzywilczek.model.map.PathfindingMap;

import java.util.List;

public abstract class LabyrinthSolver {
    private PathfindingMap map;
    public LabyrinthSolver(PathfindingMap map){
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
