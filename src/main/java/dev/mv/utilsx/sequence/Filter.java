package dev.mv.utilsx.sequence;

import dev.mv.utilsx.generic.Option;

import java.util.function.Predicate;

public class Filter<T> implements Sequence<T> {
    private Predicate<T> predicate;
    private Sequence<T> parent;

    Filter(Sequence<T> parent, Predicate<T> predicate) {
        this.predicate = predicate;
        this.parent = parent;
    }

    @Override
    public Option<T> next() {
        while (true) {
            Option<T> n = parent.next();
            if (n.isNone()) return n;
            if (predicate.test(n.getUnchecked())) return n;
        }
    }
}
