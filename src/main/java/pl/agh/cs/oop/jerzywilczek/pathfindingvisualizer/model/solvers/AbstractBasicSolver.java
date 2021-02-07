package pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.solvers;

import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.Field;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.FieldState;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.FieldType;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.PathfindingMap;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;

public abstract class AbstractBasicSolver extends AbstractLabyrinthSolver{
    private final Map<PathfindingMap.Position, PathfindingMap.Position> parent = new HashMap<>();
    private boolean finished = false;
    private final PathfindingMap map;

    protected abstract PathfindingMap.Position getNext();
    protected abstract void queuePosition(PathfindingMap.Position position);
    protected abstract boolean queueEmpty();
    protected abstract void initializeQueue();
    
    public AbstractBasicSolver(PathfindingMap map) {
        super(map);
        initializeQueue();
        queuePosition(map.getStartPosition());
        parent.put(map.getStartPosition(), map.getStartPosition());
        this.map = map;
    }

    @Override
    public List<PathfindingMap.Position> nextStep() {
        if(queueEmpty() || finished){
            finished = true;
            return new LinkedList<>();
        }
        LinkedList<PathfindingMap.Position> result = new LinkedList<>();
        PathfindingMap.Position p = getNext();
        map.getMap().get(p).setFieldState(FieldState.PROCESSED);
        result.add(p);
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                if(abs(i+j) == 1){
                    PathfindingMap.Position child = map.new Position(p.x + i, p.y + j);

                    Field field = map.getMap().get(child);
                    if((field.getFieldState() == FieldState.UNPROCESSED) && (field.getFieldType() != FieldType.WALL)){
                        queuePosition(child);
                        field.setFieldState(FieldState.BEING_PROCESSED);
                        parent.put(child, p);
                        result.add(child);
                        if(child.equals(map.getEndPosition())){
                            finished = true;
                            return result;
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

    @Override
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
