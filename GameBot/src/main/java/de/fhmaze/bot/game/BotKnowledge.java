package de.fhmaze.bot.game;

import de.fhmaze.engine.common.Direction;
import de.fhmaze.engine.game.cell.Cell;
import de.fhmaze.engine.game.cell.Finish;
import de.fhmaze.engine.game.cell.Form;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Predicate;

/**
 * Speichert und pflegt das Wissen des Bots über das Labyrinth.
 *
 * Kernideen:
 * - Jeder besuchbare Zelltyp (VisitableCell, FormCell, FinishCell) wird als Knoten
 *   in einem Graphen abgelegt.
 * - Kanten zwischen Knoten entsprechen begehbaren Nachbarschaften in einer Richtung.
 *   Rückkanten werden automatisch ergänzt (bidirektionale Verbindung).
 * - Für den eigenen Spieler gemerkte Ziele: Formulare (pro ID) und die Zielzelle (Finish).
 * - Pfadsuche erfolgt per BFS (Breadth-First Search), um kürzeste Wege zu finden.
 */
public class BotKnowledge {
    /** ID des Spielers, um nur eigene Form-/Finish-Zellen zu speichern. */
    private final int playerId;

    /** Merkt sich alle bekannten Formulare (nur für diesen Spieler), indiziert nach Form-ID. */
    private final Map<Integer, Cell> knownForms = new HashMap<>();

    /**
     * Adjazenz-Liste: Für jede besuchbare Zelle (Knoten) wird pro Richtung auf die
     * Nachbarzelle (Knoten) gezeigt. So entsteht ein ungewichteter Graph.
     */
    private final Map<Cell, Map<Direction, Cell>> adjacency = new HashMap<>();

    /** Die bekannte Zielzelle (nur wenn sie zu diesem Spieler gehört). */
    private Cell finishCell;

    /** Standard-Konstruktor mit Spieler-ID. */
    public BotKnowledge(int playerId) {
        this.playerId = playerId;
    }

    /**
     * Nimmt die aktuelle Zelle und deren Nachbarn auf und aktualisiert den Graphen.
     *
     * - Erzeugt Knoten für aktuelle Zelle und (besuchbare) Nachbarn.
     * - Trägt gerichtete Kanten current -> neighbor für jede Richtung ein.
     * - Ergänzt die Rückkante neighbor -> current automatisch über direction.back().
     * - Merkt sich eigene Form-/Finish-Zellen.
     */
    public void observeEnvironment(Cell currentCell, Cell[] neighborCells) {
        if (playerId < 0 || currentCell == null || neighborCells == null) {
            return;
        }

        observeCell(currentCell);

        // Nachbarn-Map der aktuellen Zelle (Kanten je Richtung) anlegen oder holen
        Map<Direction, Cell> neighbors = adjacency.get(currentCell);
        if (neighbors == null) {
            neighbors = new EnumMap<>(Direction.class);
            adjacency.put(currentCell, neighbors);
        }

        Direction[] directions = Direction.values();
        int length = Math.min(directions.length, neighborCells.length);

        for (int index = 0; index < length; index++) {
            Cell neighbor = neighborCells[index];
            Direction direction = directions[index];

            if (!neighbor.isVisitable()) continue;

            // Nachbarzelle registrieren.
            observeCell(neighbor);

            // Kante current --(direction)--> neighbor setzen.
            neighbors.put(direction, neighbor);

            // Rückkante neighbor --(direction.back)--> current sicherstellen.
            Map<Direction, Cell> reverseNeighbors = adjacency.get(neighbor);
            if (reverseNeighbors == null) {
                reverseNeighbors = new EnumMap<>(Direction.class);
                adjacency.put(neighbor, reverseNeighbors);
            }
            reverseNeighbors.put(direction.back(), currentCell);
        }
    }

    public Cell getForm(int formId) {
        return knownForms.get(formId);
    }

    public Cell getFinishCell() {
        return finishCell;
    }

    /**
     * Gibt die erste Richtung auf dem kürzesten Weg vom Start zur Zielzelle zurück
     *liefert null wenn kein Weg bekannt ist oder Start bereits Ziel ist
     */
    public Direction nextDirectionTowards(Cell start, Cell target) {
        List<Direction> path = shortestPath(start, target);
        if (path.isEmpty()) return null;
        return path.get(0);
    }

    /**
     * Gibt die erste Richtung auf einem kürzesten Weg zu irgendeiner unbesuchten Zelle zurück.
     */
    public Direction nextDirectionToUnvisited(Cell start) {
        List<Direction> path = shortestPath(start, cell -> !cell.isVisited());
        if (path.isEmpty()) return null;
        return path.get(0);
    }

    /**
    * Sucht nach einer verschobenen Form in der Nachbarschaft einer bekannten Position.
    *
    * @param formId Die ID der gesuchten Form
    * @param expectedCell Die Zelle, wo die Form erwartet wurde
    * @return Die neue Zelle mit der Form, oder null wenn nicht gefunden
    */
    public Cell searchDisplacedForm(int formId, Cell expectedCell) {
        if (expectedCell == null) {
            return null;
        }

        // Prüfen ob die Form noch an der erwarteten Position ist
        if (expectedCell.hasForm()) {
            Form form = expectedCell.getForm();
            if (form.takeable(playerId, formId)) {
                return expectedCell;
            }
        }

        // Suche in den direkten Nachbarn
        Map<Direction, Cell> neighbors = adjacency.get(expectedCell);
        if (neighbors != null) {
            for (Cell neighbor : neighbors.values()) {
                if (neighbor != null && neighbor.hasForm()) {
                    Form form = neighbor.getForm();
                    if (form.takeable(playerId, formId)) {
                        // Form gefunden! Aktualisiere die gespeicherte Position
                        knownForms.put(formId, neighbor);
                        return neighbor;
                    }
                }
            }
        }

        // Form nicht in unmittelbarer Nachbarschaft gefunden
        return null;
    }

    public void updateFormPosition(int formId, Cell newCell) {
        if (newCell != null && newCell.hasForm()) {
            Form form = newCell.getForm();
            if (form.takeable(playerId, formId)) {
                knownForms.put(formId, newCell);
            }
        }
    }

    /**
     * Registriert eine Zelle fürs knowledge:
     * - FormCell/FinishCell werden nur gespeichert, wenn sie zu diesem Spieler gehören
     * - für alle besuchbaren Zellen wird ein (ggf. leerer) Nachbarschaftseintrag erzeugt.
     */
    private void observeCell(Cell cell) {
        if (cell == null) {
            return;
        }

        if (cell.hasForm()) {
            Form form = cell.getForm();
            if (form.playerId() == playerId) {
                knownForms.putIfAbsent(form.formId(), cell);
            }
            if (!adjacency.containsKey(cell)) {
                // Knoten im Graphen anlegen
                adjacency.put(cell, new EnumMap<>(Direction.class));
            }
        } else if (cell.hasFinish()) {
            Finish finish = cell.getFinish();
            if (finish.playerId() == playerId) {
                finishCell = cell;
            }
            if (!adjacency.containsKey(cell)) {
                // Knoten im Graphen anlegen
                adjacency.put(cell, new EnumMap<>(Direction.class));
            }
        } else if (cell.isVisitable()) {
            if (!adjacency.containsKey(cell)) {
                // VisitableCell Knoten im Graphen anlegen
                adjacency.put(cell, new EnumMap<>(Direction.class));
            }
        }
    }

    /**
     * Kürzester-Pfad-Helfer: Überladung für ein konkretes Ziel.
     * Gibt die Richtungsfolge vom Start zum Ziel zurück (leer, wenn unbekannt/unmöglich).
     */
    private List<Direction> shortestPath(Cell start, Cell goal) {
        if (goal == null) {
            return Collections.emptyList();
        }
        return shortestPath(start, goal::equals);
    }

    /**
     * Kürzester-Pfad-Suche per BFS (Breitensuche) über den gespeicherten Graphen.
     */
    private List<Direction> shortestPath(Cell start, Predicate<Cell> goalPredicate) {
        if (start == null || goalPredicate == null || goalPredicate.test(start)) {
            return Collections.emptyList();
        }

        // Standard-BFS-Strukturen:
        // queue: Abarbeitung in Entfernungen
        // previous: merkt Vorgänger-Knoten zur späteren fadrekonstruktion
        // directionTaken: merkt die Richtung, mit der der Knoten erreicht wurde
        Queue<Cell> queue = new ArrayDeque<>();
        Map<Cell, Cell> previous = new HashMap<>();
        Map<Cell, Direction> directionTaken = new HashMap<>();

        queue.offer(start);
        previous.put(start, null);

        while (!queue.isEmpty()) {
            Cell current = queue.poll();

            // Alle bekannten Nachbarn dieser Zelle ansehen.
            Map<Direction, Cell> neighbors = adjacency.getOrDefault(current, Collections.emptyMap());

            for (Map.Entry<Direction, Cell> entry : neighbors.entrySet()) {
                Cell neighbor = entry.getValue();
                if (neighbor == null || previous.containsKey(neighbor)) {
                    continue;
                }

                // Besuch markieren und Herkunft (Knoten + Richtung) merken
                previous.put(neighbor, current);
                directionTaken.put(neighbor, entry.getKey());

                if (goalPredicate.test(neighbor)) {
                    // Ziel gefunden -> Pfad rekonstruieren und zurückgeben.
                    return buildPath(start, neighbor, previous, directionTaken);
                }

                // Nichts gefunden weiter suchen, machste nichts, steckste nicht drin
                queue.offer(neighbor);
            }
        }

        // Es gibt kein Pfad, alles eine große Verschwörung
        return Collections.emptyList();
    }

    /**
     * Rekonstruiert den Pfad vom Start zum Ziel aus den Strukturen der BFS
     * läuft rückwärts vom Ziel zum Start und sammelt dabei die genommenen Richtungen
     * kehrt sie am Ende um (Start -> Ziel) und liefert die Liste zurück
     */
    private List<Direction> buildPath(
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

    public int getPlayerId() {
        return playerId;
    }
}
