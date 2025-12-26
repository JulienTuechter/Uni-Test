package de.fhmaze.bot.strategy;

import de.fhmaze.bot.game.BotKnowledge;
import de.fhmaze.bot.game.BotPlayer;
import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.action.GoAction;
import de.fhmaze.engine.common.Direction;
import de.fhmaze.engine.common.Position;

/**
 * Erkundet die Karte und bevorzugt offene Bereiche (Abstand zu Wänden/Pfaden).
 */
public class ExplorationStrategy implements BotStrategy {
    @Override
    public Action suggestAction(BotPlayer player) {
        BotKnowledge knowledge = player.getKnowledge();
        Position currentPos = player.getPosition();

        // Suche den nächsten unbekannten Punkt, der "wertvoll" ist
        Direction bestDir = knowledge.nextDirectionTowards(currentPos, pos -> {
            // Zielbedingung für BFS: Eine unbekannte Zelle oder eine besuchte Zelle mit unbekannten Nachbarn
            // Wir suchen hier nach einer 'Frontier'-Zelle

            if (!knowledge.isUnknown(pos) && !knowledge.isVisited(pos)) {
                // Das ist eine gesehene aber nicht betretene Zelle
                // Prüfen ob sie "crowded" ist (zu nah an Wänden)
                return !knowledge.isCrowded(pos);
            }
            return false;
        });

        if (bestDir != null) {
            return new GoAction(bestDir);
        }

        // Fallback: Einfach irgendeine unbekannte/unbesuchte Zelle suchen
        bestDir = knowledge.nextDirectionTowards(currentPos,
            pos -> !knowledge.isUnknown(pos) && !knowledge.isVisited(pos));

        if (bestDir != null) {
            return new GoAction(bestDir);
        }

        return null;
    }

    @Override
    public String toString() {
        return "Exploration (Smart)";
    }
}
