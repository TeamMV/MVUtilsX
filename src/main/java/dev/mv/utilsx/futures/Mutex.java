package dev.mv.utilsx.futures;

import java.util.concurrent.locks.ReentrantLock;

public class Mutex<T> implements AutoCloseable {

    private T value;
    final ReentrantLock lock = new ReentrantLock();

    public Mutex(T value) {
        this.value = value;
    }

    public void set(T value) {
        lock.lock();
        this.value = value;
        lock.unlock();
    }

    public void setLocked(T value) {
        if (!lock.isHeldByCurrentThread()) throw new IllegalStateException("Calling Mutex.setLocked() on a Mutex locked by a separate thread!");
        this.value = value;
    }

    public T get() {
        lock.lock();
        lock.unlock();
        return value;
    }

    public T getLocked() {
        if (!lock.isHeldByCurrentThread()) throw new IllegalStateException("Calling Mutex.getLocked() on a Mutex locked by a separate thread!");
        return value;
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }

    @Override
    public void close() {
        unlock();
    }
}
