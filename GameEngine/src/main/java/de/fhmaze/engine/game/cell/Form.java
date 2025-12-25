package de.fhmaze.engine.game.cell;

import de.fhmaze.engine.game.player.Player;

public record Form(int playerId, int formId) {
    public boolean takeable(int playerId, int formCount) {
        return this.playerId == playerId && formId == formCount + 1;
    }

    public boolean takeable(Player player) {
        return takeable(player.getId(), player.getFormCount());
    }
}
