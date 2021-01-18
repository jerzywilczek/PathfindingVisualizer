package pl.agh.cs.oop.jerzywilczek.pathfindingVisualizer.model.map;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class PathfindingMap {
    private final Map<Position, Field> map = new HashMap<>();
    private final int width, height;
    private Position startPosition, endPosition;

    public class Position {
        public final int x, y;

        public Position(int x, int y) {
            if (x < 0 || x >= width || y < 0 || y >= height)
                throw new IllegalArgumentException(
                        String.format("Tried to construct a position (%d, %d) while map is a %d X %d space.", x, y, width, height)
                );
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Position position = (Position) o;

            if (x != position.x) return false;
            return y == position.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }

    public PathfindingMap(int width, int height) {
        if(width < 3 || height < 3)
            throw new IllegalArgumentException(
                    String.format("Tried to construct a PathfindingMap with width = %d and height = %d", width, height)
            );
        this.width = width;
        this.height = height;
        this.startPosition = this.new Position(1, 1);
        this.endPosition = this.new Position(width - 2, height - 2);

        IntStream.range(0, width)
                .forEach(x -> IntStream.range(0, height)
                        .mapToObj(y -> this.new Position(x, y))
                        .forEach(position -> {
                            if (
                                    position.x == 0 ||
                                    position.y == 0 ||
                                    position.x == width - 1 ||
                                    position.y == height - 1
                            )
                                map.put(position, new Field(FieldType.WALL));
                            else
                                map.put(position, new Field());
                        }));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Map<Position, Field> getMap(){
        return Collections.unmodifiableMap(map);
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Position startPosition) {
        if(map.get(startPosition).getFieldType() == FieldType.WALL)
            throw new IllegalArgumentException("Tried to set the start position to a wall field");
        this.startPosition = startPosition;
    }

    public Position getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(Position endPosition) {
        if(map.get(endPosition).getFieldType() == FieldType.WALL)
            throw new IllegalArgumentException("Tried to set the end position to a wall field");
        this.endPosition = endPosition;
    }


}
