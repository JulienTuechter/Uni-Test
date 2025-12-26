package de.fhmaze.bot.strategy;

import de.fhmaze.bot.game.BotPlayer;
import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.action.PutAction;
import de.fhmaze.engine.action.TakeAction;
import de.fhmaze.engine.game.cell.Cell;

/**
 * Sheet-Strategie: Kümmert sich um das Aufnehmen und Ablegen von Blättern.
 * Priorität: Sollte VOR der Navigation ausgeführt werden, damit verdeckte Objekte freigelegt werden.
 */
public class SheetStrategy implements BotStrategy {

    @Override
    public Action suggestAction(BotPlayer player) {
        Cell currentCell = player.getCurrentCell();

        // 1. Aufnehmen: Immer Blätter aufheben, um potenzielle Formulare darunter zu finden
        if (currentCell.hasSheet()) {
            return new TakeAction();
        }

        // 2. Ablegen: Optional, wenn wir Blätter im Inventar haben
        if (player.getSheetCount() > 0 && shouldPutSheet(player, currentCell)) {
            return new PutAction();
        }

        return null;
    }

    /**
     * Entscheidungslogik, wann ein Blatt abgelegt werden soll.
     * Aktuell konservativ (nie), kann erweitert werden (z.B. auf gegnerischen Formen).
     */
    private boolean shouldPutSheet(BotPlayer player, Cell cell) {
        // Beispiel-Logik:
        // return cell.hasForm() && cell.getForm().playerId() != player.getId();
        return false;
    }

    @Override
    public String toString() {
        return "SheetStrategy";
    }
}