package pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.solvers;

import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.PathfindingMap;

import java.util.LinkedList;

public class BFSSolver extends AbstractBasicSolver {
    private LinkedList<PathfindingMap.Position> queue;

    public BFSSolver(PathfindingMap map) {
        super(map);
    }

    @Override
    protected PathfindingMap.Position getNext() {
        return queue.pop();
    }

    @Override
    protected void queuePosition(PathfindingMap.Position position) {
        queue.addLast(position);
    }

    @Override
    protected boolean queueEmpty() {
        return queue.isEmpty();
    }

    @Override
    protected void initializeQueue() {
        queue = new LinkedList<>();
    }
}
