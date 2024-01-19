package dev.mv.utilsx.generic;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Result<T, E> {

    private boolean isOk;
    private T ok;
    private E err;

    private Result(boolean isOk, T ok, E err) {
        this.isOk = isOk;
        this.ok = ok;
        this.err = err;
    }

    public static <T, E> Result<T, E> ok(T value) {
        return new Result<>(true, value, null);
    }

    public static <T, E> Result<T, E> err(E err) {
        return new Result<>(false, null, err);
    }

    public boolean isOk() {
        return isOk;
    }

    public boolean isOkAnd(Predicate<T> predicate) {
        if (isOk) return predicate.test(ok);
        return false;
    }

    public boolean isErr() {
        return !isOk;
    }

    public boolean isErrAnd(Predicate<E> predicate) {
        if (!isOk) return predicate.test(err);
        return false;
    }

    public T get() {
        if (isErr()) {
            throw new IllegalStateException("Called Result.get() on error value: \"" + err + "\"!");
        }
        return ok;
    }

    public T get(String errorMessage) {
        if (isErr()) {
            throw new IllegalStateException(errorMessage);
        }
        return ok;
    }

    public <U extends Throwable> T get(U throwable) throws U {
        if (isErr()) {
            throw throwable;
        }
        return ok;
    }

    public T getOr(T defaultValue) {
        if (isErr()) {
            return defaultValue;
        }
        return ok;
    }

    public T getOr(Supplier<T> supplier) {
        if (isErr()) {
            return supplier.get();
        }
        return ok;
    }

    public T getUnchecked() {
        return ok;
    }

    public E getErr() {
        if (isOk) {
            throw new IllegalStateException("Called Result.getErr() on ok value: \"" + ok + "\"!");
        }
        return err;
    }

    public E getErr(String errorMessage) {
        if (isOk) {
            throw new IllegalStateException(errorMessage);
        }
        return err;
    }

    public <U extends Throwable> E getErr(U throwable) throws U {
        if (isOk()) {
            throw throwable;
        }
        return err;
    }

    public E getErrOr(E defaultValue) {
        if (isOk()) {
            return defaultValue;
        }
        return err;
    }

    public E getErrOr(Supplier<E> supplier) {
        if (isOk()) {
            return supplier.get();
        }
        return err;
    }

    public E getErrUnchecked() {
        return err;
    }

    public Option<T> ok() {
        if (isOk) return Option.some(ok);
        return Option.none();
    }

    public Option<E> err() {
        if (!isOk) return Option.some(err);
        return Option.none();
    }

    public <R> Result<R, E> map(Function<T, R> mapper) {
        if (isOk) return Result.ok(mapper.apply(ok));
        return Result.err(err);
    }

    public <R> Result<T, R> map_err(Function<E, R> mapper) {
        if (!isOk) return Result.err(mapper.apply(err));
        return Result.ok(ok);
    }

    public Result<T, E> replace(Result<T, E> newValue) {
        Result<T, E> copy = new Result<>(isOk, ok, err);
        isOk = newValue.isOk;
        ok = newValue.ok;
        err = newValue.err;
        return copy;
    }
}
