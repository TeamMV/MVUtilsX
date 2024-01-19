package dev.mv.utilsx.sequence.integer;

import dev.mv.utilsx.generic.Option;

public class IntSkip implements IntSequence{
    private IntSequence parent;
    private int skip;
    private boolean skipped;

    public IntSkip(IntSequence parent, int skip) {
        this.parent = parent;
        this.skip = skip;
    }

    @Override
    public IntOption next() {
        if (!skipped) {
            for (int i = 0; i < skip; i++) {
                if (next().isNone()) return IntOption.none();
            }
            skipped = true;
        }
        return parent.next();
    }
}
