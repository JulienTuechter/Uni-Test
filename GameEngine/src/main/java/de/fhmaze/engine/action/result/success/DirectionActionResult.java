package de.fhmaze.engine.action.result.success;

import de.fhmaze.engine.action.result.SuccessActionResult;
import de.fhmaze.engine.common.Direction;

// final = niemand darf mehr erben
public record DirectionActionResult(Direction direction) implements SuccessActionResult {}
