package de.fhmaze.communication.registry;

import de.fhmaze.communication.convert.Serializer;
import java.util.function.Function;

public class SerializerRegistry<T> implements Serializer<T> {
    private final Function<T, Serializer<? extends T>> serializerProvider;

    public SerializerRegistry(Function<T, Serializer<? extends T>> serializerProvider) {
        this.serializerProvider = serializerProvider;
    }

    @Override
    @SuppressWarnings("unchecked")
    public String serialize(T object) {
        Serializer<T> serializer = (Serializer<T>) serializerProvider.apply(object);
        return serializer.serialize(object);
    }
}
