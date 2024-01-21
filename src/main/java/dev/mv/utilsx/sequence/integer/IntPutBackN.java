package dev.mv.utilsx.sequence.integer;

import dev.mv.utilsx.collection.Vec;

public class IntPutBackN implements IntSequence{
    private IntSequence parent;
    private Vec<Integer> putBack = new Vec<>();

    public IntPutBackN(IntSequence parent) {
        this.parent = parent;
    }

    @Override
    public IntOption next() {
        if (!putBack.isEmpty()) {
            return IntOption.some(putBack.pop());
        }
        return next();
    }

    public void putBack(int value) {
        putBack.push(value);
    }
}
