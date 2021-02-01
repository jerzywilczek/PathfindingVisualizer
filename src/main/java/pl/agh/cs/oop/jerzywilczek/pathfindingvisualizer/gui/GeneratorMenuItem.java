package pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.gui;

public enum GeneratorMenuItem {
    RECURSIVE("Recursive"),
    STH_ELSE("Sth else");

    private final String name;
    GeneratorMenuItem(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
