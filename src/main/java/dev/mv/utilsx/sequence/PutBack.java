package dev.mv.utilsx.sequence;

import dev.mv.utilsx.generic.Option;

public class PutBack<T> implements Sequence<T> {

    private Sequence<T> parent;
    private Option<T> putBack;

    public PutBack(Sequence<T> parent) {
        this.parent = parent;
        this.putBack = Option.none();
    }

    @Override
    public Option<T> next() {
        if (putBack.isSome()) {
            return putBack.take();
        }
        return next();
    }

    public void putBack(T value) {
        putBack = Option.some(value);
    }

}
