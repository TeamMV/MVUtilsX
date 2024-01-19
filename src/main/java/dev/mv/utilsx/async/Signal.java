package dev.mv.utilsx.async;

import dev.mv.utilsx.futures.*;
import dev.mv.utilsx.generic.Null;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Signal implements Wake {

    final Mutex<Boolean> ready = new Mutex<>(false);
    final CondVar condition = new CondVar(ready);
    final List<Waker> wakers = new ArrayList<>(1);
    final AtomicInteger waiting = new AtomicInteger(0);

    public boolean ready() {
        return ready.get();
    }

    public void await() {
        ready.lock();
        waiting.getAndIncrement();
        while (!ready.getLocked()) {
            condition.await();
        }
        if (waiting.decrementAndGet() == 0) {
            ready.setLocked(false);
        }
        ready.unlock();
    }

    public void await(Duration timeout) {
        ready.lock();
        waiting.getAndIncrement();
        while (!ready.getLocked()) {
            condition.await(timeout);
        }
        if (waiting.decrementAndGet() == 0) {
            ready.setLocked(false);
        }
        ready.unlock();
    }

    public void await(long timeoutMillis) {
        await(Duration.ofMillis(timeoutMillis));
    }

    public Future<Null> awaitAsync() {
        return new SignalFuture(this);
    }

    @Override
    public void wake() {
        ready.lock();
        ready.set(true);
        for (Waker waker : wakers) {
            waker.wake();
        }
        wakers.clear();
        condition.signalAll();
        ready.unlock();
    }
}
