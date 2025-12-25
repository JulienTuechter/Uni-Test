package de.fhmaze.communication.registry;

import de.fhmaze.communication.convert.Converter;
import de.fhmaze.communication.convert.Parser;
import de.fhmaze.communication.convert.Serializer;
import java.util.function.Function;

public class ConverterRegistry<T> implements Converter<T> {
    private final Function<T, Serializer<T>> serializerProvider;
    private final Function<String, Parser<T>> parserProvider;

    public ConverterRegistry(
        Function<T, Serializer<T>> serializerProvider,
        Function<String, Parser<T>> parserProvider
    ) {
        this.serializerProvider = serializerProvider;
        this.parserProvider = parserProvider;
    }

    @Override
    public String serialize(T object) {
        Serializer<T> serializer = serializerProvider.apply(object);
        return serializer.serialize(object);
    }

    @Override
    public T parse(String data) {
        Parser<T> parser = parserProvider.apply(data);
        return parser.parse(data);
    }
}
