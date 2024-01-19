package dev.mv.utilsx.sequence.integer;

import java.util.function.IntPredicate;

public class IntFilter implements IntSequence {
    private IntSequence parent;
    private IntPredicate predicate;

    public IntFilter(IntSequence parent, IntPredicate predicate) {
        this.parent = parent;
        this.predicate = predicate;
    }

    @Override
    public IntOption next() {
        return parent.next().filter(predicate);
    }
}
