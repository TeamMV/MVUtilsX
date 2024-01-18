package dev.mv.utilsx.generic;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Option<T> {
    private boolean isSome;
    private T value;

    private Option(boolean isSome, T value) {
        this.isSome = isSome;
        this.value = value;
    }

    public static <T> Option<T> some(T t) {
        return new Option<>(true, t);
    }

    public static <T> Option<T> none() {
        return new Option<>(false, null);
    }

    public boolean isSome() {
        return value != null;
    }

    public boolean isNone() {
        return value == null;
    }

    public T get() {
        if (isNone()) {
            throw new IllegalStateException("Called Option.get() on empty value!");
        }
        return value;
    }

    public T get(String errorMessage) {
        if (isNone()) {
            throw new IllegalStateException(errorMessage);
        }
        return value;
    }

    public <E extends Throwable> T get(E throwable) throws E {
        if (isNone()) {
            throw throwable;
        }
        return value;
    }

    public T getOr(T defaultValue) {
        if (isNone()) {
            return defaultValue;
        }
        return value;
    }

    public T getOr(Supplier<T> supplier) {
        if (isNone()) {
            return supplier.get();
        }
        return value;
    }

    public T getUnchecked() {
        return value;
    }

    public <U> Option<U> map(Function<T, U> mapper) {
        if (isNone()) return none();
        return some(mapper.apply(value));
    }

    public Option<T> filter(Predicate<T> filter) {
        if (isNone()) return none();
        if (!filter.test(value)) return none();
        return this;
    }

    public Option<T> take() {
        if (isNone()) return none();
        isSome = false;
        var buffer = value;
        value = null;
        return Option.some(buffer);
    }
}
