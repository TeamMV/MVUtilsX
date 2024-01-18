package dev.mv.utilsx.sequence;

import dev.mv.utilsx.generic.Option;
import dev.mv.utilsx.generic.Pair;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Windows<T> implements Sequence<T[]> {

    private Sequence<T> parent;
    private int windowSize;
    private T[] window;

    Windows(Sequence<T> parent, int windowSize) {
        this.parent = parent;
        this.windowSize = windowSize;
    }

    public Option<T[]> next() {
        var next = parent.next();
        if (next.isNone()) return Option.none();
        if (window == null) {
            T value = next.getUnchecked();
            window = (T[]) Array.newInstance(value.getClass(), windowSize);
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
