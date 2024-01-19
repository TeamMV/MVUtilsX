package dev.mv.utilsx.sequence.integer;

import dev.mv.utilsx.generic.Option;
import dev.mv.utilsx.generic.Pair;
import dev.mv.utilsx.sequence.Sequence;

public class IntZip<U> implements Sequence<IntPair<U>> {
    private IntSequence parent;
    private Sequence<U> toZip;

    public IntZip(IntSequence parent, Sequence<U> toZip) {
        this.parent = parent;
        this.toZip = toZip;
    }

    @Override
    public Option<IntPair<U>> next() {
        var a = parent.next();
        var b = toZip.next();
        if (a.isNone() || b.isNone()) return Option.none();
        return Option.some(new IntPair<>(a.getUnchecked(), b.getUnchecked()));
    }
}
