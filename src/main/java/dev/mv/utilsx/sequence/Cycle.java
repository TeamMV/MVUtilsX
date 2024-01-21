package dev.mv.utilsx.sequence;

import dev.mv.utilsx.collection.Vec;
import dev.mv.utilsx.generic.Option;
public class Cycle<T> implements Sequence<T> {
    private Sequence<T> parent;
    private Vec<T> ts;
    private boolean filled;
    private int idx;

    Cycle(Sequence<T> parent) {
        this.parent = parent;
        ts = new Vec<>();
    }

    @Override
    public Option<T> next() {
        var next = parent.next();

        if (!filled) {
            if (next.isNone()) {
                filled = true;
                return Option.some(ts.get(idx++));
            }
            ts.push(next.getUnchecked());
            return next;
        }

        if (idx >= ts.len()) {
            idx = 0;
        }

        return Option.some(ts.get(idx++));
    }
}
