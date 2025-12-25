package de.fhmaze.bot.game;

import de.fhmaze.bot.knowledge.FrontierAnalyzer;
import de.fhmaze.bot.knowledge.MazeGraph;
import de.fhmaze.bot.knowledge.PathFinder;
import de.fhmaze.bot.knowledge.SymmetryNavigator;
import de.fhmaze.bot.knowledge.TargetTracker;
import de.fhmaze.engine.common.Direction;
import de.fhmaze.engine.common.Position;
import de.fhmaze.engine.game.cell.Cell;
import de.fhmaze.engine.game.cell.Form;
import java.util.List;
import java.util.Set;

public class BotKnowledge {
    private final int playerId;
    private final MazeGraph graph;
    private final TargetTracker targets;
    private final SymmetryNavigator symmetry;

    public BotKnowledge(int playerId, int sizeX, int sizeY) {
        this.playerId = playerId;
        this.graph = new MazeGraph();
        this.targets = new TargetTracker(playerId);
        this.symmetry = new SymmetryNavigator(sizeX, sizeY, graph);
    }

    public boolean isFormCoveredBySheet(Cell cell, int formId) {
        return targets.isFormCoveredBySheet(cell, formId);
    }

    public boolean isFinishCoveredBySheet(Cell cell) {
        return targets.isFinishCoveredBySheet(cell);
    }

    public void observeEnvironment(
        Cell currentCell, Cell[] neighborCells, Position currentPosition, Position[] neighborPositions
    ) {
        if (
            playerId < 0 || currentCell == null || neighborCells == null || currentPosition == null
                || neighborPositions == null
        ) {
            return;
        }

        graph.ensureNode(currentCell, currentPosition);
        symmetry.resolveHint(currentPosition);
        if (targets.markCell(currentCell)) {
            symmetry.addSymmetryHints(currentPosition);
        }

        Direction[] directions = Direction.values();
        int length = Math.min(directions.length, neighborCells.length);

        for (int index = 0; index < length; index++) {
            Cell neighbor = neighborCells[index];
            Direction direction = directions[index];
            Position neighborPos = index < neighborPositions.length ? neighborPositions[index] : null;

            if (neighbor == null || !neighbor.isVisitable()) {
                continue;
            }

            graph.ensureNode(neighbor, neighborPos);
            graph.connect(currentCell, direction, neighbor);

            if (targets.markCell(neighbor) && neighborPos != null) {
                symmetry.addSymmetryHints(neighborPos);
            }
        }
    }

    public Cell getForm(int formId) {
        return targets.getForm(formId);
    }

    public Cell getFinishCell() {
        return targets.getFinishCell();
    }

    public List<Cell> getEnemyForms() {
        return targets.getEnemyForms();
    }

    public List<Cell> getEnemyFinishCells() {
        return targets.getEnemyFinishCells();
    }

    public Cell findNearestUnsheetedEnemyTarget(Cell start) {
        Cell nearestFinish = findNearestUncovered(start, targets.getEnemyFinishCells());
        if (nearestFinish != null) {
            return nearestFinish;
        }
        return findNearestUncovered(start, targets.getEnemyForms());
    }

    private Cell findNearestUncovered(Cell start, List<Cell> targetsList) {
        Cell nearest = null;
        int shortestDistance = Integer.MAX_VALUE;

        for (Cell target : targetsList) {
            if (target.hasSheet()) continue;

            List<Direction> path = PathFinder.shortestPath(start, target, graph.adjacency());
            if (!path.isEmpty() && path.size() < shortestDistance) {
                shortestDistance = path.size();
                nearest = target;
            }
        }
        return nearest;
    }

    public Direction nextDirectionTowards(Cell start, Cell target) {
        List<Direction> path = PathFinder.shortestPath(start, target, graph.adjacency());
        if (path.isEmpty()) return null;
        return path.get(0);
    }

    public Direction nextDirectionToUnvisited(Cell start) {
        List<Direction> path = PathFinder.shortestPath(start, cell -> !cell.isVisited(), graph.adjacency());
        if (path.isEmpty()) return null;
        return path.get(0);
    }

    public Direction nextDirectionToSymmetryHint(Position currentPosition) {
        return symmetry.nextDirection(currentPosition);
    }

    public Set<Position> getSymmetryHints() {
        return symmetry.getHints();
    }

    public int countUnvisitedWithinSteps(Cell origin, int maxSteps) {
        return FrontierAnalyzer.countUnvisitedWithinSteps(origin, maxSteps, graph);
    }

    public Cell searchDisplacedForm(int formId, Cell expectedCell) {
        if (expectedCell == null) {
            return null;
        }

        if (expectedCell.hasForm()) {
            Form form = expectedCell.getForm();
            if (form.takeable(playerId, formId)) {
                return expectedCell;
            }
        }

        for (Cell neighbor : graph.neighbors(expectedCell).values()) {
            if (neighbor != null && neighbor.hasForm()) {
                Form form = neighbor.getForm();
                if (form.takeable(playerId, formId)) {
                    targets.updateFormPosition(formId, neighbor);
                    return neighbor;
                }
            }
        }

        return null;
    }

    public void updateFormPosition(int formId, Cell newCell) {
        targets.updateFormPosition(formId, newCell);
    }

    public int getPlayerId() {
        return playerId;
    }
}
