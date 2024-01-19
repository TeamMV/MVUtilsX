package dev.mv.utilsx.sequence.integer;

public class IntTake implements IntSequence{
    private IntSequence parent;
    private long limit;
    private long i;

    public IntTake(IntSequence parent, long limit) {
        this.parent = parent;
        this.limit = limit;
    }

    @Override
    public IntOption next() {
        if (i >= limit) return IntOption.none();
        i++;
        return parent.next();
    }
}
