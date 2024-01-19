package dev.mv.utilsx.sequence.integer;

import dev.mv.utilsx.generic.Option;

import java.util.ArrayList;
import java.util.List;

public class IntPutBackN implements IntSequence{
    private IntSequence parent;
    private List<Integer> putBack = new ArrayList<>();

    public IntPutBackN(IntSequence parent) {
        this.parent = parent;
    }

    @Override
    public IntOption next() {
        if (!putBack.isEmpty()) {
            return IntOption.some(putBack.removeLast());
        }
        return next();
    }

    public void putBack(int value) {
        putBack.add(value);
    }
}
