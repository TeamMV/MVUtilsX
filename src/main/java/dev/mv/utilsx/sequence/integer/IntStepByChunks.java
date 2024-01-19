package dev.mv.utilsx.sequence.integer;

import dev.mv.utilsx.generic.Option;
import dev.mv.utilsx.sequence.Sequence;

public class IntStepByChunks implements Sequence<IntSequence> {
    private IntSequence parent;
    private int step, size;

    public IntStepByChunks(IntSequence parent, int step, int size) {
        this.parent = parent;
        this.step = step;
        this.size = size;
    }

    @Override
    public Option<IntSequence> next() {
        for (int i = 0; i < step; i++) {
            parent.next();
        }
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
