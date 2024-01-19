package dev.mv.utilsx.sequence.integer;

import dev.mv.utilsx.generic.Option;
import dev.mv.utilsx.sequence.Sequence;

import java.util.Arrays;

public class IntWindows implements Sequence<int[]> {
    private IntSequence parent;
    private int windowSize;
    private int[] window;

    public IntWindows(IntSequence parent, int windowSize) {
        this.parent = parent;
        this.windowSize = windowSize;
    }

    @Override
    public Option<int[]> next() {
        var next = parent.next();
        if (next.isNone()) return Option.none();
        if (window == null) {
            int value = next.getUnchecked();
            window = new int[windowSize];
            window[0] = value;
            for (int i = 1; i < windowSize; i++) {
                var item = parent.next();
                if (item.isNone()) return Option.none();
                window[i] = item.getUnchecked();
            }
        }
        else {
            System.arraycopy(window, 1, window, 0, windowSize - 1);
            window[windowSize - 1] = next.getUnchecked();
        }
        return Option.some(Arrays.copyOf(window, windowSize));
    }
}
