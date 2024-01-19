package dev.mv.utilsx.sequence.integer;

import dev.mv.utilsx.generic.Option;
import dev.mv.utilsx.sequence.Sequence;

import java.util.ArrayList;
import java.util.List;

public class IntCycle implements IntSequence{
    private IntSequence parent;
    private List<Integer> ts;
    private boolean filled;
    private int idx;

    public IntCycle(IntSequence parent) {
        this.parent = parent;
        ts = new ArrayList<>();
    }

    @Override
    public IntOption next() {
        var next = parent.next();

        if (!filled) {
            if (next.isNone()) {
                filled = true;
                return IntOption.some(ts.get(idx++));
            }
            ts.add(next.getUnchecked());
            return next;
        }

        if (idx >= ts.size()) {
            idx = 0;
        }

        return IntOption.some(ts.get(idx++));
    }
}
