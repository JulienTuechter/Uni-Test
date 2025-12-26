package de.fhmaze.bot.strategy;

import de.fhmaze.bot.game.BotKnowledge;
import de.fhmaze.bot.game.BotPlayer;
import de.fhmaze.engine.action.Action;
import de.fhmaze.engine.action.GoAction;
import de.fhmaze.engine.action.TakeAction;
import de.fhmaze.engine.common.Direction;
import de.fhmaze.engine.common.Position;

/**
 * Verantwortlich für das Finden von Zielen, wenn deren genaue Position unklar ist.
 * Nutzt Sheets, Symmetrie und Umgebungssuche.
 */
public class SearchStrategy implements BotStrategy {
    private final int targetFormId;

    public SearchStrategy(int targetFormId) {
        this.targetFormId = targetFormId;
    }

    @Override
    public Action suggestAction(BotPlayer player) {
        BotKnowledge knowledge = player.getKnowledge();
        Position currentPos = player.getPosition();

        // 1. Stehen wir auf einem Sheet? Aufdecken!
        if (knowledge.isSheet(currentPos)) {
            // Versuch das Sheet aufzunehmen/aufzudecken
            return new TakeAction();
        }

        // 2. Wissen wir, wo die Form ist? (Wurde vielleicht von NavStrategy nicht gefunden weil Weg blockiert)
        Position knownPos = knowledge.getFormPosition(targetFormId);
        if (knownPos != null) {
            // Wegberechnung dahin versuchen (selbst wenn NavigationStrategy scheiterte, vllt neuer Weg?)
            Direction dir = knowledge.nextDirectionTowards(currentPos, knownPos);
            if (dir != null) return new GoAction(dir);
        }

        // 3. Symmetrie nutzen: Wo starteten wir? Wo ist das symmetrische Gegenstück?
        // Annahme: Form 1 liegt oft punktsymmetrisch zu Form 1 des Gegners oder Startpunkten.
        // Einfacherer Ansatz: Suche symmetrisch zu bereits gefundenen eigenen Punkten.
        // Wenn wir noch gar nichts wissen, erkunden wir Sheets.

        // Suche gezielt nach Sheets (potenzielle Verstecke)
        Direction sheetDir = knowledge.nextDirectionTowards(currentPos, knowledge::isSheet);
        if (sheetDir != null) {
            return new GoAction(sheetDir);
        }

        // 4. Verschobene Form suchen (lokale Umgebung scannen)
        // Falls wir dachten, sie sei hier, aber sie ist weg -> Spiral search oder Random walk
        // Dies wird teilweise durch Exploration abgedeckt.

        return null; // Fallback an ExplorationStrategy
    }

    @Override
    public String toString() {
        return "Search (Form " + targetFormId + ")";
    }
}
