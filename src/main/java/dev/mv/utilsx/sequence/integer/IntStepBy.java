package dev.mv.utilsx.sequence.integer;

public class IntStepBy implements IntSequence{
    private IntSequence parent;
    private int step;

    public IntStepBy(IntSequence parent, int step) {
        this.parent = parent;
        this.step = step;
    }

    @Override
    public IntOption next() {
        var next = parent.next();
        for (int i = 0; i < step - 1; i++) {
            next = parent.next();
        }
        return next;
    }
}
