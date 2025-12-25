package de.fhmaze.bot.knowledge;

import de.fhmaze.engine.game.cell.Cell;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class FrontierAnalyzer {
    private FrontierAnalyzer() {}

    /*
    * Zählt die Anzahl der unbesuchten Zellen innerhalb einer gegebenen Anzahl
     von Schritten vom Ursprungszelle aus
    */
    public static int countUnvisitedWithinSteps(Cell origin, int maxSteps, MazeGraph graph) {
        if (origin == null || maxSteps <= 0 || graph == null) {
            return 0;
        }

        Queue<CellDepth> queue = new ArrayDeque<>();
        Set<Cell> seen = new HashSet<>();

        queue.offer(new CellDepth(origin, 0));
        seen.add(origin);

        int unvisitedCount = 0;

        while (!queue.isEmpty()) {
            CellDepth current = queue.poll();
            if (current.depth > 0 && !current.cell.isVisited()) {
                unvisitedCount++;
            }

            if (current.depth == maxSteps) {
                continue;
            }

            for (Cell neighbor : graph.neighbors(current.cell).values()) {
                if (neighbor == null || seen.contains(neighbor)) {
                    continue;
                }
                seen.add(neighbor);
                queue.offer(new CellDepth(neighbor, current.depth + 1));
            }
        }

        return unvisitedCount;
    }

    private record CellDepth(Cell cell, int depth) {}
}
