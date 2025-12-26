package de.fhmaze.communication.registry;

import de.fhmaze.communication.convert.Converter;
import de.fhmaze.communication.convert.Parser;
import de.fhmaze.communication.convert.Serializer;
import java.util.function.Function;

public class ConverterRegistry<T> implements Converter<T> {
    private final Function<T, Serializer<? extends T>> serializerProvider;
    private final Function<String, Parser<? extends T>> parserProvider;

    public ConverterRegistry(
        Function<T, Serializer<? extends T>> serializerProvider,
        Function<String, Parser<? extends T>> parserProvider
    ) {
        this.serializerProvider = serializerProvider;
        this.parserProvider = parserProvider;
    }

    @Override
    @SuppressWarnings("unchecked")
    public String serialize(T object) {
        Serializer<T> serializer = (Serializer<T>) serializerProvider.apply(object);
        return serializer.serialize(object);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T parse(String data) {
        Parser<T> parser = (Parser<T>) parserProvider.apply(data);
        return parser.parse(data);
    }
}
