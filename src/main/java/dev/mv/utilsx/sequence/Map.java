package dev.mv.utilsx.sequence;

import dev.mv.utilsx.generic.Option;

import java.util.function.Function;

public class Map<T, R> implements Sequence<R> {
    private Function<T, R> mapper;
    private Sequence<T> parent;

    Map(Sequence<T> parent, Function<T, R> mapper) {
        this.parent = parent;
        this.mapper = mapper;
    }

    @Override
    public Option<R> next() {
        return parent.next().map(mapper);
    }
}
