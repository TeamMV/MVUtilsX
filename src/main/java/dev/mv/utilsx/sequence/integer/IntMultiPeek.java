package dev.mv.utilsx.sequence.integer;

import dev.mv.utilsx.collection.Vec;

public class IntMultiPeek implements IntSequence{
    private IntSequence parent;
    private Vec<Integer> peeked = new Vec<>();
    private int current = 0;

    public IntMultiPeek(IntSequence parent) {
        this.parent = parent;
    }

    public IntOption peek() {
        if (peeked.len() > current) {
            return IntOption.some(peeked.get(current++));
        }
        var next = parent.next();
        if (next.isNone()) return IntOption.none();
        current++;
        peeked.push(next.getUnchecked());
        return next;
    }

    public void resetPeek() {
        current = 0;
    }

    @Override
    public IntOption next() {
        if (!peeked.isEmpty()) {
            current--;
            return IntOption.some(peeked.popFirst());
        }
        return parent.next();
    }
}
