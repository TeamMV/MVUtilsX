package dev.mv.utilsx.sequence;

import dev.mv.utilsx.generic.Option;

import java.util.HashSet;
import java.util.Set;

public class Distinct<T> implements Sequence<T> {
    private Sequence<T> parent;
    private Set<T> used;

    public Distinct(Sequence<T> parent) {
        this.parent = parent;
        this.used = new HashSet<>();
    }

    @Override
    public Option<T> next() {
        var next = parent.next();
        if (next.isNone()) return Option.none();
        if (!used.contains(next.getUnchecked())) {
            used.add(next.getUnchecked());
            return next;
        }
        return Option.none();
    }
}
