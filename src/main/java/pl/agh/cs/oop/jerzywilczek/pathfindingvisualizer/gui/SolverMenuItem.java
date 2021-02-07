package pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.gui;

public enum SolverMenuItem {
    BFS("BFS"),
    DFS("DFS"),
    A_STAR("A*");


    private final String name;
    SolverMenuItem(String name){
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }
}
