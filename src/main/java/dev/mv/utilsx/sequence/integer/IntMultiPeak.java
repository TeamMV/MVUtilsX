package dev.mv.utilsx.sequence.integer;

import dev.mv.utilsx.generic.Option;
import dev.mv.utilsx.sequence.Sequence;

import java.util.ArrayList;
import java.util.List;

public class IntMultiPeak implements IntSequence{
    private IntSequence parent;
    private List<Integer> peeked = new ArrayList<>();
    private int current = 0;

    public IntMultiPeak(IntSequence parent) {
        this.parent = parent;
    }

    public IntOption peek() {
        if (peeked.size() > current) {
            return IntOption.some(peeked.get(current++));
        }
        var next = parent.next();
        if (next.isNone()) return IntOption.none();
        current++;
        peeked.add(next.getUnchecked());
        return next;
    }

    public void resetPeek() {
        current = 0;
    }

    @Override
    public IntOption next() {
        if (!peeked.isEmpty()) {
            current--;
            return IntOption.some(peeked.removeFirst());
        }
        return parent.next();
    }
}
