package dev.mv.utilsx.sequence;

import dev.mv.utilsx.generic.Option;

import java.util.function.Predicate;

public class SkipWhile<T> implements Sequence<T> {
    private Sequence<T> parent;
    private Predicate<T> test;
    private boolean skipped;

    SkipWhile(Sequence<T> parent, Predicate<T> test) {
        this.parent = parent;
        this.test = test;
    }

    @Override
    public Option<T> next() {
        if (!skipped) {
            var n = next();
            if (n.isNone()) return Option.none();
            while (n.isSome()) {
                if (!test.test(n.getUnchecked())) return Option.none();
                n = next();
            }
            skipped = true;
        }

        return parent.next();
    }
}
