package dev.mv.utilsx.sequence;

import dev.mv.utilsx.generic.Option;

import java.util.function.Function;

public class FilterMap<T, R> implements Sequence<R> {

    private Sequence<T> parent;
    private Function<T, Option<R>> mapper;

    public FilterMap(Sequence<T> parent, Function<T, Option<R>> mapper) {
        this.parent = parent;
        this.mapper = mapper;
    }

    @Override
    public Option<R> next() {
        Option<T> next;
        while (true) {
            next = parent.next();
            if (next.isNone()) return Option.none();
            Option<R> value = mapper.apply(next.getUnchecked());
            if (value.isSome()) return value;
        }
    }
}
