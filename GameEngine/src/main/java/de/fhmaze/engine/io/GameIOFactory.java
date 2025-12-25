package de.fhmaze.engine.io;

// Entwurfsmuster (Erzeugung): Abstract Factory
public interface GameIOFactory {
    GameInputReader createGameInputReader();
    ActionSender createActionSender();
    GameLogger createGameLogger();
}
