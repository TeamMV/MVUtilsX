package dev.mv.utilsx.sequence.integer;

import dev.mv.utilsx.generic.Option;
import dev.mv.utilsx.sequence.Sequence;

import java.util.function.IntFunction;

public class IntFilterMap<R> implements Sequence<R> {
    private IntSequence parent;
    private IntFunction<Option<R>> mapper;

    public IntFilterMap(IntSequence parent, IntFunction<Option<R>> mapper) {
        this.parent = parent;
        this.mapper = mapper;
    }

    @Override
    public Option<R> next() {
        IntOption next;
        while (true) {
            next = parent.next();
            if (next.isNone()) return Option.none();
            Option<R> value = mapper.apply(next.getUnchecked());
            if (value.isSome()) return value;
        }
    }
}
