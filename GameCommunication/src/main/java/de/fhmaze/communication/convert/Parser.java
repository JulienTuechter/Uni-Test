package de.fhmaze.communication.convert;

public interface Parser<T> {
    T parse(String data);
}
