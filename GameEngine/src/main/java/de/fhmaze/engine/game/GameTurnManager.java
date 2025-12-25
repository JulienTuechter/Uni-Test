package de.fhmaze.engine.game;

import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.game.cell.CellTurnHandler;
import de.fhmaze.engine.game.player.Player;
import de.fhmaze.engine.game.player.PlayerTurnHandler;
import de.fhmaze.engine.io.GameLogger;
import de.fhmaze.engine.turn.TurnManager;
import de.fhmaze.engine.turn.listener.TurnListener;

public class GameTurnManager extends TurnManager implements TurnListener {
    private final GameLogger logger;

    protected GameTurnManager(Player player, GameLogger logger) {
        this.logger = logger;
        addListeners(player);
    }

    private void addListeners(Player player) {
        PlayerTurnHandler playerTurnHandler = new PlayerTurnHandler(player);
        CellTurnHandler cellTurnHandler = new CellTurnHandler(player);
        addTurnListeners(playerTurnHandler, cellTurnHandler, this, player);
        addCellStatusListener(cellTurnHandler);
    }

    @Override
    public void onTurnFailure(Action action, ActionResult result) {
        String actionName = action.getClass().getSimpleName();
        String resultName = result.getClass().getSimpleName();
        logger.logMessage("Turn failed: %s -> %s", actionName, resultName);
    }
}
