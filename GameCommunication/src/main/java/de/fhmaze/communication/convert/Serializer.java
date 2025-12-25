package de.fhmaze.communication.convert;

public interface Serializer<T> {
    String serialize(T object);
}
