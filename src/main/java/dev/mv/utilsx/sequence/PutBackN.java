package dev.mv.utilsx.sequence;

import dev.mv.utilsx.collection.Vec;
import dev.mv.utilsx.generic.Option;

public class PutBackN<T> implements Sequence<T> {

    private Sequence<T> parent;
    private Vec<T> putBack = new Vec<>();;

    public PutBackN(Sequence<T> parent) {
        this.parent = parent;
    }

    @Override
    public Option<T> next() {
        if (!putBack.isEmpty()) {
            return Option.some(putBack.pop());
        }
        return next();
    }

    public void putBack(T value) {
        putBack.push(value);
    }

}
