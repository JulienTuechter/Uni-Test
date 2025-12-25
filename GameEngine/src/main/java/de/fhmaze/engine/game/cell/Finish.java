package de.fhmaze.engine.game.cell;

import de.fhmaze.engine.game.player.Player;

public record Finish(int playerId, int formCount) {
    public boolean finishable(int playerId, int formCount) {
        return this.playerId == playerId && this.formCount == formCount;
    }

    public boolean finishable(Player player) {
        return finishable(player.getId(), player.getFormCount());
    }
}
