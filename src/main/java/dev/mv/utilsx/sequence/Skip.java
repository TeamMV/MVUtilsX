package dev.mv.utilsx.sequence;

import dev.mv.utilsx.generic.Option;

public class Skip<T> implements Sequence<T> {
    private Sequence<T> parent;
    private int skip;
    private boolean skipped;

    Skip(Sequence<T> parent, int skip) {
        this.parent = parent;
        this.skip = skip;
    }

    @Override
    public Option<T> next() {
        if (!skipped) {
            for (int i = 0; i < skip; i++) {
                if (next().isNone()) return Option.none();
            }
            skipped = true;
        }
        return parent.next();
    }
}
