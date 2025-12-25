package de.fhmaze.engine.game.player;

import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.action.GoAction;
import de.fhmaze.engine.action.PutAction;
import de.fhmaze.engine.action.TakeAction;
import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.action.result.FormActionResult;
import de.fhmaze.engine.action.result.SheetActionResult;
import de.fhmaze.engine.common.Direction;
import de.fhmaze.engine.turn.listener.TurnListener;

public class PlayerTurnHandler implements TurnListener {
    private final Player player;

    public PlayerTurnHandler(Player player) {
        this.player = player;
    }

    @Override
    public void onTurnSuccess(Action action, ActionResult result) {
        if (action instanceof GoAction(Direction direction)) {
            player.goDirection(direction);
        } else if (action instanceof TakeAction) {
            if (result instanceof FormActionResult)
                player.takeForm();
            else if (result instanceof SheetActionResult)
                player.takeSheet();
        } else if (action instanceof PutAction) {
            player.putSheet();
        }
    }
}
