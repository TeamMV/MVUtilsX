package dev.mv.utilsx.sequence.integer;

import dev.mv.utilsx.generic.Option;
import dev.mv.utilsx.sequence.Sequence;

public class IntIntZip implements Sequence<IntIntPair> {
    private IntSequence parent, toZip;

    public IntIntZip(IntSequence parent, IntSequence toZip) {
        this.parent = parent;
        this.toZip = toZip;
    }

    @Override
    public Option<IntIntPair> next() {
        var a = parent.next();
        var b = toZip.next();
        if (a.isNone() || b.isNone()) return Option.none();
        return Option.some(new IntIntPair(a.getUnchecked(), b.getUnchecked()));
    }
}
