package dev.mv.utilsx.sequence.integer;

import dev.mv.utilsx.generic.Option;
import dev.mv.utilsx.sequence.Sequence;

public class IntChunks implements Sequence<IntSequence> {
    private IntSequence parent;
    private int size;

    public IntChunks(IntSequence parent, int size) {
        this.parent = parent;
        this.size = size;
    }

    @Override
    public Option<IntSequence> next() {
        return Option.some(new IntSequence() {
            private int used = 0;

            @Override
            public IntOption next() {
                used++;
                if (used > size) return IntOption.none();
                return parent.next();
            }
        });
    }
}
