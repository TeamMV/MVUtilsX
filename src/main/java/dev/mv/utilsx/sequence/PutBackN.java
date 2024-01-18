package dev.mv.utilsx.sequence;

import dev.mv.utilsx.generic.Option;

import java.util.ArrayList;
import java.util.List;

public class PutBackN<T> implements Sequence<T> {

    private Sequence<T> parent;
    private List<T> putBack = new ArrayList<>();;

    public PutBackN(Sequence<T> parent) {
        this.parent = parent;
    }

    @Override
    public Option<T> next() {
        if (!putBack.isEmpty()) {
            return Option.some(putBack.removeLast());
        }
        return next();
    }

    public void putBack(T value) {
        putBack.add(value);
    }

}
