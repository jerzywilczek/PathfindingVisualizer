package pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.gui;

public enum SolverMenuItem {
    BFS("BFS"),
    DFS("DFS");


    private final String name;
    SolverMenuItem(String name){
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }
}
