package dev.mv.utilsx.sequence;

import dev.mv.utilsx.generic.Option;

import java.util.function.Predicate;

public class TakeWhile<T> implements Sequence<T> {
    private Sequence<T> parent;
    private Predicate<T> test;

    TakeWhile(Sequence<T> parent, Predicate<T> test) {
        this.parent = parent;
        this.test = test;
    }

    @Override
    public Option<T> next() {
        var n = parent.next();
        if (n.isNone()) {
            return Option.none();
        }
        if (!test.test(n.getUnchecked())) return Option.none();
        return n;
    }
}
