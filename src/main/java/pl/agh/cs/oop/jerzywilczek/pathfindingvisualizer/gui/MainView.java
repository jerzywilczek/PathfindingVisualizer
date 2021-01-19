package pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.gui;

import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.PathfindingMap;

public class MainView {
    private final MainController controller;
    private final PathfindingMap map;
    private final Drawer drawer;

    public MainView(MainController controller){
        this.controller = controller;
        this.map = new PathfindingMap(50, 50);
        this.drawer = new Drawer(controller.getCanvas(), map);
        drawer.fullUpdate();
    }
}
