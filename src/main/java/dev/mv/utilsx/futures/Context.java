package dev.mv.utilsx.futures;

public class Context {

    private Waker waker;

    public Context(Waker waker) {
        this.waker = waker;
    }

    public Waker waker() {
        return waker;
    }
}
