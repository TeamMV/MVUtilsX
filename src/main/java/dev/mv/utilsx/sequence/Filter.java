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
        return parent.next().filter(predicate);
    }
}
