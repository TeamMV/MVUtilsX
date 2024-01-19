package dev.mv.utilsx.async;

import dev.mv.utilsx.futures.Context;
import dev.mv.utilsx.futures.Future;
import dev.mv.utilsx.futures.Poll;
import dev.mv.utilsx.generic.Null;

public class SignalFuture implements Future<Null> {

    private final Signal signal;
    private boolean started = false;

    SignalFuture(Signal signal) {
        this.signal = signal;
    }

    @Override
    public Poll<Null> poll(Context context) {
        try {
            signal.ready.lock();
            if (signal.ready.getLocked()) return new Poll<>(Null.INSTANCE);
            if (!started) {
                signal.wakers.add(context.waker());
            }
            return new Poll<>();
        } finally {
            signal.ready.unlock();
        }
    }
}
