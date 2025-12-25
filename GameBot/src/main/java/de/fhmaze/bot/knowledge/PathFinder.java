package de.fhmaze.bot.knowledge;

import de.fhmaze.engine.common.Direction;
import de.fhmaze.engine.game.cell.Cell;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Predicate;

public class PathFinder {
    private PathFinder() {}

    public static List<Direction> shortestPath(Cell start, Cell goal, Map<Cell, Map<Direction, Cell>> adjacency) {
        if (goal == null) {
            return Collections.emptyList();
        }
        return shortestPath(start, goal::equals, adjacency);
    }

    public static List<Direction> shortestPath(
        Cell start, Predicate<Cell> goalPredicate, Map<Cell, Map<Direction, Cell>> adjacency
    ) {
        if (start == null || goalPredicate == null || goalPredicate.test(start)) {
            return Collections.emptyList();
        }

        Queue<Cell> queue = new ArrayDeque<>();
        Map<Cell, Cell> previous = new HashMap<>();
        Map<Cell, Direction> directionTaken = new HashMap<>();

        queue.offer(start);
        previous.put(start, null);

        while (!queue.isEmpty()) {
            Cell current = queue.poll();
            Map<Direction, Cell> neighbors = adjacency.getOrDefault(current, Collections.emptyMap());

            for (Map.Entry<Direction, Cell> entry : neighbors.entrySet()) {
                Cell neighbor = entry.getValue();
                if (neighbor == null || previous.containsKey(neighbor)) {
                    continue;
                }

                previous.put(neighbor, current);
                directionTaken.put(neighbor, entry.getKey());

                if (goalPredicate.test(neighbor)) {
                    return buildPath(start, neighbor, previous, directionTaken);
                }

                queue.offer(neighbor);
            }
        }

        return Collections.emptyList();
    }

    private static List<Direction> buildPath(
        Cell start, Cell goal, Map<Cell, Cell> previous, Map<Cell, Direction> directionTaken
    ) {
        List<Direction> path = new ArrayList<>();
        Cell current = goal;

        while (!current.equals(start)) {
            Direction direction = directionTaken.get(current);
            if (direction == null) {
                return Collections.emptyList();
            }
            path.add(direction);
            current = previous.get(current);
            if (current == null) {
                return Collections.emptyList();
            }
        }

        Collections.reverse(path);
        return path;
    }
}
