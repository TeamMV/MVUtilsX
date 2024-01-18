package dev.mv.utilsx.sequence;

import dev.mv.utilsx.generic.Option;

public class Take<T> implements Sequence<T> {
    private Sequence<T> parent;
    private long limit;
    private long i;

    public Take(Sequence<T> parent, long limit) {
        this.parent = parent;
        this.limit = limit;
    }

    @Override
    public Option<T> next() {
        if (i >= limit) return Option.none();
        i++;
        return parent.next();
    }
}
