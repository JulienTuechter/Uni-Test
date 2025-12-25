package de.fhmaze.engine.game.cell;

import de.fhmaze.engine.game.player.Player;

public record Sheet() {
    public boolean takeable(int sheetCount) {
        return sheetCount > 0;
    }

    public boolean takeable(Player player) {
        return takeable(player.getSheetCount());
    }
}
