package de.fhmaze.bot.game;

import de.fhmaze.bot.strategy.BotStrategy;
import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.action.GoAction;
import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.common.Direction;
import de.fhmaze.engine.game.cell.Cell;
import de.fhmaze.engine.game.player.Player;
import de.fhmaze.engine.game.player.PlayerParams;
import de.fhmaze.engine.io.GameLogger;
import java.util.List;
import java.util.Stack;

public class BotPlayer extends Player {
    private final Stack<Cell> path = new Stack<>();
    private final Stack<Direction> turns = new Stack<>();
    private final List<BotStrategy> strategies;
    private final BotKnowledge knowledge;
    private final GameLogger logger;

    public BotPlayer(PlayerParams params, List<BotStrategy> strategies, GameLogger logger) {
        super(params);
        this.strategies = strategies;
        this.logger = logger;

        Cell currentCell = getCurrentCell();
        Direction direction = getDirection();

        // Startposition speichern
        path.push(currentCell);
        turns.push(direction);

        // Wissen konfigurieren
        knowledge = new BotKnowledge(params.id());
    }

    @Override
    public void onTurnSuccess(Action action, ActionResult result) {
        Cell currentCell = getCurrentCell();
        Direction direction = getDirection();

        // nur Richtungsänderungen berücksichtigen
        if (!(action instanceof GoAction)) return;

        // wenn aktuelle Zelle noch nicht im Pfad, dann hinzufügen
        if (!path.contains(currentCell)) {
            path.push(currentCell);
            turns.push(direction);
        } else {
            // zurücksetzen bis zur aktuellen Zelle
            while (!path.isEmpty() && !path.peek().equals(currentCell)) {
                // letztes entfernen, Backtracking
                path.pop();
                turns.pop();
            }
        }
    }

    @Override
    public Action chooseNextAction() {
        Cell currentCell = getCurrentCell();
        Cell[] neighborCells = getNeighborCells();

        // Position loggen
        GameLogger logger = getLogger();
        logger.logMessage("Position: %s", getPosition());

        // Umgebung beobachten
        knowledge.observeEnvironment(currentCell, neighborCells);

        // Strategien der Reihe nach durchprobieren (Chain of Responsibility)
        for (BotStrategy strategy : strategies) {
            Action action = strategy.suggestAction(this);
            if (action != null) {
                logger.logMessage("Strategy chosen: %s -> %s", strategy, action);
                return action;
            }
        }

        // Sollte eigentlich nie erreicht werden, da FallbackStrategy immer eine Action liefert
        throw new IllegalStateException("No strategy could provide an action");
    }

    public Stack<Cell> getPath() {
        return path;
    }

    public Stack<Direction> getTurns() {
        return turns;
    }

    public BotKnowledge getKnowledge() {
        return knowledge;
    }

    public GameLogger getLogger() {
        return logger;
    }
}
