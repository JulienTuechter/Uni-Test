package de.fhmaze.engine.game.player;

import de.fhmaze.engine.common.Position;
import de.fhmaze.engine.game.cell.Maze;

public record PlayerParams(int id, Position start, int sheetCount, Maze maze) {}
