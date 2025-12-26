package de.fhmaze.bot.strategy;

import de.fhmaze.bot.game.BotKnowledge;
import de.fhmaze.bot.game.BotPlayer;
import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.action.FinishAction;
import de.fhmaze.engine.action.GoAction;
import de.fhmaze.engine.action.TakeAction;
import de.fhmaze.engine.common.Direction;
import de.fhmaze.engine.common.Position;
import de.fhmaze.engine.game.cell.Cell;
import de.fhmaze.engine.game.cell.Finish;
import de.fhmaze.engine.game.cell.Form;

public class NavigationStrategy implements BotStrategy {
    @Override
    public Action suggestAction(BotPlayer player) {
        Cell currentCell = player.getCurrentCell();
        BotKnowledge knowledge = player.getKnowledge();
        int playerId = player.getId();
        int formCount = player.getFormCount();

        // 1. Interaktion mit aktueller Zelle
        if (canFinish(currentCell, playerId, formCount)) return new FinishAction();
        if (canTake(currentCell, playerId, formCount)) return new TakeAction();

        // 2. Interaktion mit direkten Nachbarn
        for (Direction dir : Direction.values()) {
            Cell neighbor = player.getNeighborCell(dir);
            if (neighbor == null) continue;

            // Wenn Ziel oder Form direkt nebenan ist -> hingehen
            if (
                (canFinish(neighbor, playerId, formCount) || canTake(neighbor, playerId, formCount))
                    && !neighbor.hasEnemy()
            ) {
                return new GoAction(dir);
            }
        }

        // 3. Pfad zum nächsten bekannten Ziel berechnen
        Position targetPos = null;

        // Zuerst nach nächster Form schauen
        int nextFormId = formCount + 1;
        Position formPos = knowledge.getFormPosition(nextFormId);
        if (formPos != null) {
            targetPos = formPos;
        } else {
            // Wenn alle Formen da sind, zum Finish
            // (Wir nehmen an, wir wissen wie viele Formen es gibt, oder Finish ist das Ziel wenn keine Form bekannt)
            Position finishPos = knowledge.getFinishPosition();
            if (finishPos != null && formCount >= getRequiredFormsForFinish(knowledge)) {
                targetPos = finishPos;
            }
        }

        if (targetPos != null) {
            Direction dir = knowledge.nextDirectionTowards(player.getPosition(), targetPos);
            if (dir != null) {
                // Prüfen ob der nächste Schritt sicher ist (Gegner vermeiden macht AvoidanceStrategy, aber sicher ist sicher)
                Cell nextCell = player.getNeighborCell(dir);
                if (nextCell != null && !nextCell.hasEnemy()) {
                    return new GoAction(dir);
                }
            }
        }

        return null;
    }

    private boolean canTake(Cell cell, int pid, int count) {
        if (cell != null && cell.hasForm()) {
            Form f = cell.getForm();
            return f.playerId() == pid && f.formId() == count + 1;
        }
        return false;
    }

    private boolean canFinish(Cell cell, int pid, int count) {
        if (cell != null && cell.hasFinish()) {
            Finish f = cell.getFinish();
            return (f.playerId() == pid || f.playerId() == -1) && count >= f.formCount();
        }
        return false;
    }

    // Kleiner Hack: Wir wissen nicht genau, wie viele Forms das Finish braucht ohne das Finish-Objekt zu sehen.
    // Aber wir gehen zum Finish, wenn wir keine Forms mehr finden oder das Finish "active" wirkt.
    private int getRequiredFormsForFinish(BotKnowledge k) {
        // Default auf 0 oder Logik verbessern, wenn FinishCellStatus formCount speichert
        return 0;
    }

    @Override
    public String toString() {
        return "Navigation";
    }
}
