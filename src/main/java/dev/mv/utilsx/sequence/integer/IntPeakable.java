package dev.mv.utilsx.sequence.integer;

public class IntPeakable implements IntSequence{
    private IntSequence parent;
    private IntOption buffer;

    public IntPeakable(IntSequence parent) {
        this.parent = parent;
    }

    public IntOption peek() {
        if (buffer != null) return buffer;
        buffer = parent.next();
        return buffer;
    }

    @Override
    public IntOption next() {
        if (buffer == null) return parent.next();
        IntOption temp = buffer;
        buffer = null;
        return temp;
    }
}
