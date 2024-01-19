package dev.mv.utilsx.sequence.integer;

import dev.mv.utilsx.generic.Option;
import dev.mv.utilsx.sequence.Sequence;

import java.util.function.IntPredicate;
import java.util.function.Predicate;

public class IntSkipWhile implements IntSequence{
    private IntSequence parent;
    private IntPredicate test;
    private boolean skipped;

    public IntSkipWhile(IntSequence parent, IntPredicate test) {
        this.parent = parent;
        this.test = test;
    }

    @Override
    public IntOption next() {
        if (!skipped) {
            var n = next();
            if (n.isNone()) return IntOption.none();
            while (n.isSome()) {
                if (!test.test(n.getUnchecked())) return IntOption.none();
                n = next();
            }
            skipped = true;
        }

        return parent.next();
    }
}
