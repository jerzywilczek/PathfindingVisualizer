package pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.gui.animation;

import javafx.animation.AnimationTimer;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.map.PathfindingMap;
import pl.agh.cs.oop.jerzywilczek.pathfindingvisualizer.model.solvers.AbstractLabyrinthSolver;

import java.util.LinkedList;
import java.util.List;

public class Animator {
    private Animation currentAnimation;
    private boolean running = false;
    private final Drawer drawer;
    private final List<AnimationFinishedObserver> animationFinishedObservers = new LinkedList<>();
    private boolean solvingAnimation = false;

    public Animator(Drawer drawer) {
        this.drawer = drawer;
    }

    public void animateBatch(LinkedList<PathfindingMap.Position> updateFields) {
        prepareAnimation();
        currentAnimation = new BatchAnimator(updateFields);
        currentAnimation.start();
    }

    private void transitionToBatch(LinkedList<PathfindingMap.Position> updateFields) {
        if (isAnimating())
            currentAnimation.stop();
        currentAnimation = new BatchAnimator(updateFields);
        if(running)
            currentAnimation.start();
    }

    public void animateSolving(AbstractLabyrinthSolver solver) {
        prepareAnimation();
        solvingAnimation = true;
        currentAnimation = new StepAnimator(solver);
        currentAnimation.start();
    }

    private void prepareAnimation() {
        if (isAnimating()) {
            skip();
        }
        running = true;
    }

    private void animationFinished() {
        currentAnimation.stop();
        currentAnimation = null;
        running = false;
        animationFinishedObservers.forEach(observer -> observer.animationFinished(solvingAnimation));
        solvingAnimation = false;
    }

    public void skip() {
        if (isAnimating()) {
            currentAnimation.skip();
        }
    }

    public void toggleRunning() {
        if (!isAnimating()) {
            return;
        }
        if (running) {
            currentAnimation.stop();
            running = false;
        } else {
            currentAnimation.start();
            running = true;
        }
    }

    public boolean isAnimating() {
        return currentAnimation != null;
    }

    public void addObserver(AnimationFinishedObserver observer) {
        animationFinishedObservers.add(observer);
    }

    public void removeObserver(AnimationFinishedObserver observer) {
        animationFinishedObservers.remove(observer);
    }

    private static abstract class Animation extends AnimationTimer {
        public abstract void skip();
    }

    private class BatchAnimator extends Animation {
        private final LinkedList<PathfindingMap.Position> updateFields;

        public BatchAnimator(LinkedList<PathfindingMap.Position> updateFields) {
            this.updateFields = updateFields;
        }

        @Override
        public void handle(long now) {
            if (updateFields.isEmpty()) {
                animationFinished();
                return;
            }
            drawer.updatePosition(updateFields.remove(0));
        }

        @Override
        public void skip() {
            drawer.fullUpdate();
            animationFinished();
        }
    }

    private class StepAnimator extends Animation {
        private final AbstractLabyrinthSolver solver;

        public StepAnimator(AbstractLabyrinthSolver solver) {
            this.solver = solver;
        }

        @Override
        public void handle(long now) {
            if (solver.finished()) {
                this.stop();
                transitionToBatch(new LinkedList<>(solver.getSolvedPath()));
                return;
            }
            drawer.updatePositions(solver.nextStep());
        }

        @Override
        public void skip() {
            this.stop();
            while (!solver.finished())
                solver.nextStep();
            drawer.fullUpdate();
            transitionToBatch(new LinkedList<>(solver.getSolvedPath()));
        }
    }
}
