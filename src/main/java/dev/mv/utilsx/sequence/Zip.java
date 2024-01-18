package dev.mv.utilsx.sequence;

import dev.mv.utilsx.generic.Option;
import dev.mv.utilsx.generic.Pair;

public class Zip<T, U> implements Sequence<Pair<T, U>> {
    private Sequence<T> parent;
    private Sequence<U> toZip;

    Zip(Sequence<T> parent, Sequence<U> toZip) {
        this.parent = parent;
        this.toZip = toZip;
    }

    @Override
    public Option<Pair<T, U>> next() {
        var a = parent.next();
        var b = toZip.next();
        if (a.isNone() || b.isNone()) return Option.none();
        return Option.some(new Pair<>(a.getUnchecked(), b.getUnchecked()));
    }
}
