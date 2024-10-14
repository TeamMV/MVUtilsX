package dev.mv.utilsx.sequence;

import dev.mv.utilsx.generic.Indexed;
import dev.mv.utilsx.generic.Option;

public class Enumerate<T> implements Sequence<Indexed<T>> {
    private Sequence<T> parent;
    private int i = 0;

    Enumerate(Sequence<T> parent) {
        this.parent = parent;
    }

    @Override
    public Option<Indexed<T>> next() {
        var next = parent.next();
        if (next.isSome()) {
            return Option.some(new Indexed<>(i++, next.getUnchecked()));
        }
        return Option.none();
    }
}
