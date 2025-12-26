package de.fhmaze.communication.registry;

import de.fhmaze.communication.convert.Parser;
import java.util.function.Function;

public class ParserRegistry<T> implements Parser<T> {
    private final Function<String, Parser<? extends T>> parserProvider;

    public ParserRegistry(Function<String, Parser<? extends T>> parserProvider) {
        this.parserProvider = parserProvider;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T parse(String data) {
        Parser<T> parser = (Parser<T>) parserProvider.apply(data);
        return parser.parse(data);
    }
}
