package dev.mv.utilsx.sequence.integer;

import dev.mv.utilsx.collection.Vec;

public class IntCycle implements IntSequence{
    private IntSequence parent;
    private Vec<Integer> ts;
    private boolean filled;
    private int idx;

    public IntCycle(IntSequence parent) {
        this.parent = parent;
        ts = new Vec<>();
    }

    @Override
    public IntOption next() {
        var next = parent.next();

        if (!filled) {
            if (next.isNone()) {
                filled = true;
                return IntOption.some(ts.get(idx++));
            }
            ts.push(next.getUnchecked());
            return next;
        }

        if (idx >= ts.len()) {
            idx = 0;
        }

        return IntOption.some(ts.get(idx++));
    }
}
