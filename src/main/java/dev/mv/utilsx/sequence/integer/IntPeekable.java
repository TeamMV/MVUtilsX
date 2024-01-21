package dev.mv.utilsx.sequence.integer;

public class IntPeekable implements IntSequence{
    private IntSequence parent;
    private IntOption buffer;

    public IntPeekable(IntSequence parent) {
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
