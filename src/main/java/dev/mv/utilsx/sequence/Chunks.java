package dev.mv.utilsx.sequence;

import dev.mv.utilsx.generic.Option;

public class Chunks<T> implements Sequence<Sequence<T>> {
    private Sequence<T> parent;
    private int size;

    Chunks(Sequence<T> parent, int size) {
        this.parent = parent;
        this.size = size;
    }

    @Override
    public Option<Sequence<T>> next() {
        return Option.some(new Sequence<T>() {
            private int used = 0;

            @Override
            public Option<T> next() {
                used++;
                if (used > size) return Option.none();
                return parent.next();
            }
        });
    }
}
