package de.fhmaze.engine.common;

public enum Direction {
    NORTH(0, -1),
    EAST(1, 0),
    SOUTH(0, 1),
    WEST(-1, 0);

    private final int deltaX;
    private final int deltaY;

    Direction(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public Direction right() {
        // ordinal = Name wird zu Index umgewandelt
        return values()[(ordinal() + 1) % values().length];
    }

    public Direction left() {
        return right().right().right();
    }

    public Direction back() {
        return right().right();
    }

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }
}
