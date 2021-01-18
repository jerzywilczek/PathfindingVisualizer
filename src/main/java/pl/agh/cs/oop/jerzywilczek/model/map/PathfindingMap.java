package pl.agh.cs.oop.jerzywilczek.model.map;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class PathfindingMap {
    private final Map<Position, Field> map = new HashMap<>();
    private final int width, height;

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
        this.width = width;
        this.height = height;

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
}
