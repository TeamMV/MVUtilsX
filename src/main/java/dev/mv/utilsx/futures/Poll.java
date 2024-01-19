package dev.mv.utilsx.futures;

public class Poll<T> {

    private final boolean isReady;
    private final T value;

    public Poll() {
        isReady = false;
        value = null;
    }

    public Poll(T value) {
        isReady = true;
        this.value = value;
    }

    public boolean isReady() {
        return isReady;
    }

    public boolean isPending() {
        return !isReady;
    }

    public T get() {
        return value;
    }
}
