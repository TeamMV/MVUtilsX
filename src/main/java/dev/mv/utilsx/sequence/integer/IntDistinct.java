package dev.mv.utilsx.sequence.integer;

import dev.mv.utilsx.generic.Option;
import dev.mv.utilsx.sequence.Sequence;

import java.util.Set;

public class IntDistinct implements IntSequence{
    private IntSequence parent;
    private Set<Integer> used;

    public IntDistinct(IntSequence parent) {
        this.parent = parent;
    }

    @Override
    public IntOption next() {
        var next = parent.next();
        if (next.isNone()) return IntOption.none();
        if (!used.contains(next.getUnchecked())) {
            used.add(next.getUnchecked());
            return next;
        }
        return IntOption.none();
    }
}
