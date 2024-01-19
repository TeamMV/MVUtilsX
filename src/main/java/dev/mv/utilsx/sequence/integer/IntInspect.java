package dev.mv.utilsx.sequence.integer;

import dev.mv.utilsx.generic.Option;

import java.util.function.IntConsumer;

public class IntInspect implements IntSequence{
    private IntSequence parent;
    private IntConsumer inspector;

    public IntInspect(IntSequence parent, IntConsumer inspector) {
        this.parent = parent;
        this.inspector = inspector;
    }

    @Override
    public IntOption next() {
        var next = parent.next();
        if (next.isSome()) {
            inspector.accept(next.getUnchecked());
            return next;
        }
        return IntOption.none();
    }
}
