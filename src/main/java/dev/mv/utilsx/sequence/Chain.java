package dev.mv.utilsx.sequence;

import dev.mv.utilsx.generic.Option;

public class Chain<T> implements Sequence<T> {
    private Sequence<T> parent, toChain;
    private boolean second = false;

    public Chain(Sequence<T> parent, Sequence<T> toChain) {
        this.parent = parent;
        this.toChain = toChain;
    }

    @Override
    public Option<T> next() {
        if (second) return toChain.next();
        var next = parent.next();
        if (next.isNone()) {
            second = true;
            return toChain.next();
        }
        return next;
    }
}
