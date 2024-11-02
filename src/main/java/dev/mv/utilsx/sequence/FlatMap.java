package dev.mv.utilsx.sequence;

import dev.mv.utilsx.collection.IntoSequence;
import dev.mv.utilsx.generic.Option;

import java.util.function.Function;

public class FlatMap<T, R> implements Sequence<R> {

    private Sequence<T> parent;
    private Function<T, IntoSequence<R>> mapper;

    private Sequence<R> nextMapped;

    public FlatMap(Sequence<T> parent, Function<T, IntoSequence<R>> mapper) {
        this.parent = parent;
        this.mapper = mapper;
    }

    @Override
    public Option<R> next() {
        Option<R> next = Option.none();
        if (nextMapped != null) next = nextMapped.next();
        if (next.isNone()) {
            Option<T> parentNext = parent.next();
            if (parentNext.isNone()) {
                return Option.none();
            }
            nextMapped = mapper.apply(parentNext.getUnchecked()).iter();
            return next();
        } else {
            return next;
        }
    }
}
