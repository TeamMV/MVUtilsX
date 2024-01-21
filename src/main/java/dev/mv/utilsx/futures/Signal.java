package dev.mv.utilsx.futures;

import dev.mv.utilsx.collection.Vec;
import dev.mv.utilsx.generic.Null;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class Signal implements Wake {

    private final Mutex<Boolean> ready = new Mutex<>(false);
    private final CondVar condition = new CondVar(ready);
    private final Vec<Waker> wakers = new Vec<>();
    private final AtomicInteger waiting = new AtomicInteger(0);

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

    public static class SignalFuture implements Future<Null> {

        private final Signal signal;
        private boolean started = false;

        private SignalFuture(Signal signal) {
            this.signal = signal;
        }

        @Override
        public Poll<Null> poll(Context context) {
            try {
                signal.ready.lock();
                if (signal.ready.getLocked()) return new Poll<>(Null.INSTANCE);
                if (!started) {
                    signal.wakers.push(context.waker());
                }
                return new Poll<>();
            } finally {
                signal.ready.unlock();
            }
        }
    }
}
