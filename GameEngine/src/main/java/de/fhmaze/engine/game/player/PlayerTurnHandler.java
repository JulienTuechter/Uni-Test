package de.fhmaze.engine.game.player;

import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.action.GoAction;
import de.fhmaze.engine.action.PutAction;
import de.fhmaze.engine.action.TakeAction;
import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.action.result.success.FormActionResult;
import de.fhmaze.engine.action.result.success.SheetActionResult;
import de.fhmaze.engine.common.Direction;
import de.fhmaze.engine.turn.listener.TurnListener;

public class PlayerTurnHandler implements TurnListener {
    private final Player player;

    public PlayerTurnHandler(Player player) {
        this.player = player;
    }

    @Override
    public void onTurnSuccess(Action action, ActionResult result) {
        switch (action) {
            case GoAction goAction -> handleGoAction(goAction);
            case TakeAction _ -> handleTakeAction(result);
            case PutAction _ -> handlePutAction();
            default -> {}
        }
    }

    private void handleGoAction(GoAction goAction) {
        Direction direction = goAction.direction();
        player.goDirection(direction);
    }

    private void handleTakeAction(ActionResult result) {
        if (result instanceof FormActionResult) {
            player.takeForm();
        } else if (result instanceof SheetActionResult) {
            player.takeSheet();
        }
    }

    private void handlePutAction() {
        player.putSheet();
    }
}
