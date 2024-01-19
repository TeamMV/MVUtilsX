package dev.mv.utilsx.sequence.integer;

import dev.mv.utilsx.generic.Option;

import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;

public class IntOption {
    private boolean isSome;
    private int value;

    private IntOption(boolean isSome, int value) {
        this.isSome = isSome;
        this.value = value;
    }

    public static IntOption some(int t) {
        return new IntOption(true, t);
    }

    public static IntOption none() {
        return new IntOption(false, 0);
    }

    public boolean isSome() {
        return isSome;
    }

    public boolean isNone() {
        return !isSome;
    }

    public int get() {
        if (isNone()) {
            throw new IllegalStateException("Called IntOption.get() on empty value!");
        }
        return value;
    }

    public int get(String errorMessage) {
        if (isNone()) {
            throw new IllegalStateException(errorMessage);
        }
        return value;
    }

    public <E extends Throwable> int get(E throwable) throws E {
        if (isNone()) {
            throw throwable;
        }
        return value;
    }

    public int getOr(int defaultValue) {
        if (isNone()) {
            return defaultValue;
        }
        return value;
    }

    public int getOr(IntSupplier supplier) {
        if (isNone()) {
            return supplier.getAsInt();
        }
        return value;
    }

    public int getUnchecked() {
        return value;
    }

    public <U> Option<U> map(IntFunction<U> mapper) {
        if (isNone()) return Option.none();
        return Option.some(mapper.apply(value));
    }

    public IntOption filter(IntPredicate filter) {
        if (isNone()) return none();
        if (!filter.test(value)) return none();
        return this;
    }

    public IntOption take() {
        if (isNone()) return none();
        isSome = false;
        var buffer = value;
        value = 0;
        return IntOption.some(buffer);
    }

    public IntOption replace(IntOption newValue) {
        IntOption copy = new IntOption(isSome, value);
        isSome = newValue.isSome;
        value = newValue.value;
        return copy;
    }
}
