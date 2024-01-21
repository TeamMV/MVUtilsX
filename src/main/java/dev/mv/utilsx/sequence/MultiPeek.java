package dev.mv.utilsx.sequence;

import dev.mv.utilsx.collection.Vec;
import dev.mv.utilsx.generic.Option;

public class MultiPeek<T> implements Sequence<T> {

    private Sequence<T> parent;
    private Vec<T> peeked = new Vec<>();
    private int current = 0;

    public MultiPeek(Sequence<T> parent) {
        this.parent = parent;
    }

    @Override
    public Option<T> next() {
        if (!peeked.isEmpty()) {
            current--;
            return Option.some(peeked.popFirst());
        }
        return parent.next();
    }

    public Option<T> peek() {
        if (peeked.len() > current) {
            return Option.some(peeked.get(current++));
        }
        var next = parent.next();
        if (next.isNone()) return Option.none();
        current++;
        peeked.push(next.getUnchecked());
        return next;
    }

    public void resetPeek() {
        current = 0;
    }

}
