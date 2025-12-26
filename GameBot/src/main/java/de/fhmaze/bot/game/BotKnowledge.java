package de.fhmaze.bot.game;

import de.fhmaze.engine.common.Direction;
import de.fhmaze.engine.common.Position;
import de.fhmaze.engine.game.cell.Cell;
import de.fhmaze.engine.game.cell.Finish;
import de.fhmaze.engine.game.cell.Form;
import de.fhmaze.engine.game.cell.Sheet;

import java.util.*;
import java.util.function.Predicate;

public class BotKnowledge {
    private final int playerId;

    // Speicheroptimierung: Map statt Cell-Objekten
    // Key: Position (Record hat equals/hashCode implementiert)
    // Value: 0 = Unknown (implizit), 1 = Floor, 2 = Wall, 3 = Sheet
    private final Map<Position, Byte> grid = new HashMap<>();
    private final Set<Position> visited = new HashSet<>();

    // Wichtige Orte
    private final Map<Integer, Position> knownForms = new HashMap<>();
    private final List<Position> knownSheets = new ArrayList<>();
    private Position finishPosition;

    // Map-Grenzen für Symmetrie
    private int minX = Integer.MAX_VALUE;
    private int maxX = Integer.MIN_VALUE;
    private int minY = Integer.MAX_VALUE;
    private int maxY = Integer.MIN_VALUE;

    public BotKnowledge(int playerId) {
        this.playerId = playerId;
    }

    /**
     * Aktualisierte Methode: Benötigt die aktuelle Position des Spielers,
     * da die Zellen selbst nicht wissen, wo sie sind.
     */
    public void observeEnvironment(Position currentPos, Cell currentCell, Cell[] neighborCells) {
        if (currentCell == null) return;

        // 1. Eigene Position verarbeiten
        updateCellData(currentPos, currentCell);
        visited.add(currentPos);

        // 2. Nachbarn verarbeiten (Index im Array entspricht Direction.ordinal())
        if (neighborCells != null) {
            Direction[] dirs = Direction.values();
            for (int i = 0; i < dirs.length && i < neighborCells.length; i++) {
                Cell neighbor = neighborCells[i];
                Direction dir = dirs[i];

                if (neighbor != null) {
                    // Position des Nachbarn berechnen
                    Position neighborPos = calculatePosition(currentPos, dir);
                    updateCellData(neighborPos, neighbor);
                }
            }
        }
    }

    /**
     * Hilfsmethode zur Berechnung einer neuen Position, da Position kein .add() hat.
     */
    private Position calculatePosition(Position start, Direction dir) {
        return new Position(start.x() + dir.getDeltaX(), start.y() + dir.getDeltaY());
    }

    private void updateCellData(Position pos, Cell cell) {
        // Grenzen aktualisieren
        updateBounds(pos);

        // Typ bestimmen
        byte type = 1; // Floor

        if (!cell.isVisitable()) {
            type = 2; // Wall
        } else if (cell.hasSheet()) {
            type = 3; // Sheet
            if (!knownSheets.contains(pos)) {
                knownSheets.add(pos);
            }
        }

        // Wenn wir wissen, dass hier KEIN Sheet mehr ist (weil wir es z.B. aufgenommen haben), entfernen
        if (!cell.hasSheet() && knownSheets.contains(pos)) {
            knownSheets.remove(pos);
        }

        grid.put(pos, type);

        // Inhalte prüfen
        if (cell.hasForm()) {
            Form form = cell.getForm();
            if (form.playerId() == playerId) {
                knownForms.put(form.formId(), pos);
            }
        } else if (cell.hasFinish()) {
            Finish finish = cell.getFinish();
            if (finish.playerId() == playerId || finish.playerId() == -1) {
                finishPosition = pos;
            }
        }
    }

    private void updateBounds(Position pos) {
        if (pos.x() < minX) minX = pos.x();
        if (pos.x() > maxX) maxX = pos.x();
        if (pos.y() < minY) minY = pos.y();
        if (pos.y() > maxY) maxY = pos.y();
    }

    // --- Pfadfindung (BFS) ---

    public Direction nextDirectionTowards(Position start, Predicate<Position> goalPredicate) {
        // BFS findet den kürzesten Weg zu einer Position, die das Prädikat erfüllt
        if (goalPredicate.test(start)) return null;

        Queue<Position> queue = new ArrayDeque<>();
        Map<Position, Position> previous = new HashMap<>();
        Map<Position, Direction> moveDir = new HashMap<>(); // Merkt sich die erste Richtung
        Set<Position> seen = new HashSet<>();

        queue.add(start);
        seen.add(start);

        while (!queue.isEmpty()) {
            Position current = queue.poll();

            if (goalPredicate.test(current)) {
                return reconstructFirstStep(start, current, previous, moveDir);
            }

            for (Direction dir : Direction.values()) {
                Position neighbor = calculatePosition(current, dir);

                // Prüfen ob begehbar (im Grid vorhanden und keine Wand)
                // Wir behandeln 'unbekannt' (null) als begehbar für die Planung,
                // damit der Bot auch in unbekannte Gebiete läuft.
                Byte type = grid.get(neighbor);
                boolean isWall = (type != null && type == 2);

                if (!isWall && !seen.contains(neighbor)) {
                    seen.add(neighbor);
                    previous.put(neighbor, current);

                    // Wir speichern die Richtung, die wir von 'current' genommen haben
                    moveDir.put(neighbor, dir);
                    queue.add(neighbor);
                }
            }
        }
        return null;
    }

    public Direction nextDirectionTowards(Position start, Position target) {
        return nextDirectionTowards(start, p -> p.equals(target));
    }

    private Direction reconstructFirstStep(Position start, Position end, Map<Position, Position> prev, Map<Position, Direction> dirs) {
        Position curr = end;
        Direction firstMove = null;

        // Wir laufen rückwärts vom Ziel zum Start
        while (!curr.equals(start)) {
            Position parent = prev.get(curr);
            // Die Richtung, die vom Parent zu curr führte
            firstMove = dirs.get(curr);
            curr = parent;
        }
        return firstMove;
    }

    // --- Getter & Helper ---

    public Position getFormPosition(int id) { return knownForms.get(id); }
    public Position getFinishPosition() { return finishPosition; }
    public List<Position> getKnownSheets() { return knownSheets; } // Rückgabetyp korrigiert auf List<Position>

    public boolean isSheet(Position pos) {
        return knownSheets.contains(pos);
    }

    public boolean isVisited(Position pos) { return visited.contains(pos); }

    public boolean isUnknown(Position pos) { return !grid.containsKey(pos); }

    public boolean isCrowded(Position pos) {
        int crowdedNeighbors = 0;
        for (Direction d : Direction.values()) {
            Position n = calculatePosition(pos, d);
            Byte type = grid.get(n);
            // Wand oder besucht zählt als "eng"
            if ((type != null && type == 2) || visited.contains(n)) {
                crowdedNeighbors++;
            }
        }
        return crowdedNeighbors > 1; // Mehr als 1 Hindernis -> Eng
    }

    // Symmetrie Berechnung
    public Position predictSymmetricPosition(Position source) {
        if (minX == Integer.MAX_VALUE || maxX == Integer.MIN_VALUE) return null;
        int symX = (maxX + minX) - source.x();
        int symY = (maxY + minY) - source.y();
        return new Position(symX, symY);
    }
}