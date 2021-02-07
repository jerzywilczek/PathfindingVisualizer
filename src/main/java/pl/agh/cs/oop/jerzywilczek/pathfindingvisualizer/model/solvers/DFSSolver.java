package pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.solvers;

import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.PathfindingMap;

import java.util.LinkedList;

public class DFSSolver extends AbstractBasicSolver {
    private LinkedList<PathfindingMap.Position> stack;

    public DFSSolver(PathfindingMap map) {
        super(map);
    }

    @Override
    protected PathfindingMap.Position getNext() {
        return stack.pop();
    }

    @Override
    protected void queuePosition(PathfindingMap.Position position) {
        stack.addFirst(position);
    }

    @Override
    protected boolean queueEmpty() {
        return stack.isEmpty();
    }

    @Override
    protected void initializeQueue() {
        stack = new LinkedList<>();
    }
}
