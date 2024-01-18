package dev.mv.utilsx.sequence;

import dev.mv.utilsx.generic.Option;

public class StepByChunks<T> implements Sequence<Sequence<T>> {
    private Sequence<T> parent;
    private int step, size;

    StepByChunks(Sequence<T> parent, int step, int size) {
        this.parent = parent;
        this.step = step;
        this.size = size;
    }

    @Override
    public Option<Sequence<T>> next() {
        for (int i = 0; i < step; i++) {
            parent.next();
        }
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
