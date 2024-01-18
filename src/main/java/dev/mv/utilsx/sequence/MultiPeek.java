package dev.mv.utilsx.sequence;

import dev.mv.utilsx.generic.Option;

import java.util.ArrayList;
import java.util.List;

public class MultiPeek<T> implements Sequence<T> {

    private Sequence<T> parent;
    private List<T> peeked = new ArrayList<>();
    private int current = 0;

    public MultiPeek(Sequence<T> parent) {
        this.parent = parent;
    }

    @Override
    public Option<T> next() {
        if (!peeked.isEmpty()) {
            current--;
            return Option.some(peeked.removeFirst());
        }
        return parent.next();
    }

    public Option<T> peek() {
        if (peeked.size() > current) {
            return Option.some(peeked.get(current++));
        }
        var next = parent.next();
        if (next.isNone()) return Option.none();
        current++;
        peeked.add(next.getUnchecked());
        return next;
    }

    public void resetPeek() {
        current = 0;
    }

}
