package dev.mv.utilsx.sequence;

import dev.mv.utilsx.generic.Option;

import java.util.function.Predicate;

public class TakeWhile<T> implements Sequence<T> {
    private Sequence<T> parent;
    private Predicate<T> test;
    private boolean ended = false;

    TakeWhile(Sequence<T> parent, Predicate<T> test) {
        this.parent = parent;
        this.test = test;
    }

    @Override
    public Option<T> next() {
        if (ended) return Option.none();
        var n = parent.next();
        if (n.isNone()) {
            return Option.none();
        }
        if (!test.test(n.getUnchecked())) {
            ended = true;
            return Option.none();
        }
        return n;
    }
}
