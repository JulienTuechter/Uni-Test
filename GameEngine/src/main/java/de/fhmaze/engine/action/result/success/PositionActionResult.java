package de.fhmaze.engine.action.result.success;

import de.fhmaze.engine.action.result.SuccessActionResult;
import de.fhmaze.engine.common.Position;

public record PositionActionResult(Position position) implements SuccessActionResult {}
