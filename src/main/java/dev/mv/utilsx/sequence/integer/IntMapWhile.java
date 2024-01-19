package dev.mv.utilsx.sequence.integer;

import dev.mv.utilsx.generic.Option;
import dev.mv.utilsx.sequence.Sequence;

import java.util.function.IntFunction;

public class IntMapWhile<R> implements Sequence<R> {
    private IntSequence parent;
    private IntFunction<Option<R>> mapper;
    private boolean done;

    public IntMapWhile(IntSequence parent, IntFunction<Option<R>> mapper) {
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
