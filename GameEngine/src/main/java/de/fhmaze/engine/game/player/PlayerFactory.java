package de.fhmaze.engine.game.player;

// Entwurfsmuster (Erzeugung): Abstract Factory
public interface PlayerFactory {
    Player createPlayer(PlayerParams params);
}
