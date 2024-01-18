package dev.mv.utilsx.sequence;

import dev.mv.utilsx.generic.Option;

public class Peekable<T> implements Sequence<T> {
    private Sequence<T> parent;
    private Option<T> buffer;

    Peekable(Sequence<T> parent) {
        this.parent = parent;
    }

    public Option<T> peek() {
        if (buffer != null) return buffer;
        buffer = parent.next();
        return buffer;
    }

    @Override
    public Option<T> next() {
        if (buffer == null) return parent.next();
        Option<T> temp = buffer;
        buffer = null;
        return temp;
    }
}
