package dev.mv.utilsx.sequence.integer;

public class IntChain implements IntSequence{
    private IntSequence parent, toChain;
    private boolean second = false;

    public IntChain(IntSequence parent, IntSequence toChain) {
        this.parent = parent;
        this.toChain = toChain;
    }

    @Override
    public IntOption next() {
        if (second) return toChain.next();
        var next = parent.next();
        if (next.isNone()) {
            second = true;
            return toChain.next();
        }
        return next;
    }
}
