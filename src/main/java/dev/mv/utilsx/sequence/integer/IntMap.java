package dev.mv.utilsx.sequence.integer;

import dev.mv.utilsx.generic.Option;
import dev.mv.utilsx.sequence.Sequence;

import java.util.function.IntFunction;

public class IntMap<R> implements Sequence<R> {
    private IntSequence parent;
    private IntFunction<R> mapper;

    public IntMap(IntSequence parent, IntFunction<R> mapper) {
        this.parent = parent;
        this.mapper = mapper;
    }

    @Override
    public Option<R> next() {
        return parent.next().map(mapper);
    }
}
