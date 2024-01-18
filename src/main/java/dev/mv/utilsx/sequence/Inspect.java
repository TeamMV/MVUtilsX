package dev.mv.utilsx.sequence;

import dev.mv.utilsx.generic.Option;

import java.util.function.Consumer;

public class Inspect<T> implements Sequence<T> {
    private Sequence<T> parent;
    private Consumer<T> inspector;

    Inspect(Sequence<T> parent, Consumer<T> inspector) {
        this.parent = parent;
        this.inspector = inspector;
    }

    @Override
    public Option<T> next() {
        var next = parent.next();
        if (next.isSome()) {
            inspector.accept(next.getUnchecked());
            return next;
        }
        return Option.none();
    }
}
