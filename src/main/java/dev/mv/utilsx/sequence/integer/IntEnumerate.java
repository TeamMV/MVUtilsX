package dev.mv.utilsx.sequence.integer;

import dev.mv.utilsx.generic.Indexed;
import dev.mv.utilsx.generic.Option;
import dev.mv.utilsx.sequence.Sequence;

public class IntEnumerate implements Sequence<IntIndexed> {
    private IntSequence parent;
    private int i;

    public IntEnumerate(IntSequence parent) {
        this.parent = parent;
    }

    @Override
    public Option<IntIndexed> next() {
        var next = parent.next();
        if (next().isSome()) {
            return Option.some(new IntIndexed(i++, next.getUnchecked()));
        }
        return Option.none();
    }
}
