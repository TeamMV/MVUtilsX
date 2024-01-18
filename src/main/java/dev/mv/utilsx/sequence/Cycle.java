package dev.mv.utilsx.sequence;

import dev.mv.utilsx.generic.Option;

import java.util.ArrayList;
import java.util.List;

public class Cycle<T> implements Sequence<T> {
    private Sequence<T> parent;
    private List<T> ts;
    private boolean filled;
    private int idx;

    Cycle(Sequence<T> parent) {
        this.parent = parent;
        ts = new ArrayList<>();
    }

    @Override
    public Option<T> next() {
        var next = parent.next();

        if (!filled) {
            if (next.isNone()) {
                filled = true;
                return Option.some(ts.get(idx++));
            }
            ts.add(next.getUnchecked());
            return next;
        }

        if (idx >= ts.size()) {
            idx = 0;
        }

        return Option.some(ts.get(idx++));
    }
}
