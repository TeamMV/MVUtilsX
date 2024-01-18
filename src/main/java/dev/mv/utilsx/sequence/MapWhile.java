package dev.mv.utilsx.sequence;

import dev.mv.utilsx.generic.Option;

import java.util.function.Function;

public class MapWhile<T, R> implements Sequence<R> {
    private Function<T, Option<R>> mapper;
    private Sequence<T> parent;
    private boolean done;

    MapWhile(Sequence<T> parent, Function<T, Option<R>> mapper) {
        this.parent = parent;
        this.mapper = mapper;
    }

    @Override
    public Option<R> next() {
        if (done) return Option.none();
        var item = parent.next();
        if (item.isNone()) return Option.none();
        var value = item.getUnchecked();
        var mapped = mapper.apply(value);
        if (mapped.isNone()) {
            done = true;
            return Option.none();
        }
        return Option.some(mapped.getUnchecked());
    }
}
