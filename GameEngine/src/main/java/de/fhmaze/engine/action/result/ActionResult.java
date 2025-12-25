package de.fhmaze.engine.action.result;

// sealed = nur explizit erlaubte Klassen dürfen erben
public sealed interface ActionResult permits OkayActionResult, NotOkayActionResult {}
