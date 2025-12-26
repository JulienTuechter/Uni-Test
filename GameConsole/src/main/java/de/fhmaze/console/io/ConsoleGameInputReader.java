package de.fhmaze.console.io;

import de.fhmaze.communication.convert.Parser;
import de.fhmaze.engine.action.result.ActionResult;
import de.fhmaze.engine.common.Position;
import de.fhmaze.engine.init.MazeInfo;
import de.fhmaze.engine.init.PlayerInfo;
import de.fhmaze.engine.io.GameInputReader;
import de.fhmaze.engine.status.CellStatus;
import de.fhmaze.engine.turn.TurnInfo;
import java.io.InputStream;
import java.util.Scanner;

public class ConsoleGameInputReader implements GameInputReader {
    private final Scanner input;
    private final Parser<ActionResult> actionResultParser;
    private final Parser<CellStatus> cellStatusParser;

    public ConsoleGameInputReader(
        InputStream in,
        Parser<ActionResult> actionResultParser,
        Parser<CellStatus> cellStatusParser
    ) {
        input = new Scanner(in);
        this.actionResultParser = actionResultParser;
        this.cellStatusParser = cellStatusParser;
    }

    @Override
    public MazeInfo readMazeInfo() {
        int sizeX = input.nextInt();
        int sizeY = input.nextInt();
        int level = input.nextInt();
        input.nextLine();
        return new MazeInfo(sizeX, sizeY, level);
    }

    @Override
    public PlayerInfo readPlayerInfo() {
        int id = input.nextInt();
        Position start = new Position(input.nextInt(), input.nextInt());
        int sheetCount = input.hasNextInt() ? input.nextInt() : 0;
        input.nextLine();
        return new PlayerInfo(id, start, sheetCount);
    }

    @Override
    public boolean hasNextTurn() {
        return input.hasNext();
    }

    @Override
    public TurnInfo readTurnInfo() {
        ActionResult lastActionResult = readActionResult();
        CellStatus currentCellStatus = readCellStatus();
        CellStatus[] neighborCellStatuses = new CellStatus[] {
            readCellStatus(),
            readCellStatus(),
            readCellStatus(),
            readCellStatus()
        };
        return new TurnInfo(lastActionResult, currentCellStatus, neighborCellStatuses);
    }

    private ActionResult readActionResult() {
        String data = input.nextLine();
        return actionResultParser.parse(data);
    }

    private CellStatus readCellStatus() {
        String data = input.nextLine();
        return cellStatusParser.parse(data);
    }

    @Override
    public void close() {
        input.close();
    }
}
