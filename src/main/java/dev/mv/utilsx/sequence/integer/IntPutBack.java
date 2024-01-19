package dev.mv.utilsx.sequence.integer;

import dev.mv.utilsx.generic.Option;

public class IntPutBack implements IntSequence{
    private IntSequence parent;
    private IntOption putBack;

    public IntPutBack(IntSequence parent) {
        this.parent = parent;
        putBack = IntOption.none();
    }

    @Override
    public IntOption next() {
        if (putBack.isSome()) {
            return putBack.take();
        }
        return next();
    }

    public void putBack(int value) {
        putBack = IntOption.some(value);
    }
}
