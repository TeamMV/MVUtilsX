package dev.mv.utilsx.futures;

public class Waker implements Wake {

    Wake wake;

    public Waker(Wake wake) {
        this.wake = wake;
    }

    @Override
    public void wake() {
        wake.wake();
    }

}
