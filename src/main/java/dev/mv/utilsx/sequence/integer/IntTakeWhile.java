package dev.mv.utilsx.sequence.integer;

import dev.mv.utilsx.generic.Option;
import dev.mv.utilsx.sequence.Sequence;

import java.util.function.IntPredicate;
import java.util.function.Predicate;

public class IntTakeWhile implements IntSequence{
    private IntSequence parent;
    private IntPredicate test;
    private boolean ended = false;

    public IntTakeWhile(IntSequence parent, IntPredicate test) {
        this.parent = parent;
        this.test = test;
    }

    @Override
    public IntOption next() {
        if (ended) return IntOption.none();
        var n = parent.next();
        if (n.isNone()) {
            return IntOption.none();
        }
        if (!test.test(n.getUnchecked())) {
            ended = true;
            return IntOption.none();
        }
        return n;
    }
}
