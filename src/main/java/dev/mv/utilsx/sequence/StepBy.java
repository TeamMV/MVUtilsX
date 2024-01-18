package dev.mv.utilsx.sequence;

import dev.mv.utilsx.generic.Option;

public class StepBy<T> implements Sequence<T> {
    private Sequence<T> parent;
    private int step;

    StepBy(Sequence<T> parent, int step) {
        this.parent = parent;
        this.step = step;
    }

    @Override
    public Option<T> next() {
        var next = parent.next();
        for (int i = 0; i < step - 1; i++) {
            next = parent.next();
        }
        return next;
    }
}
