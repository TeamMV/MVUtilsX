package dev.mv.utilsx.futures;

import dev.mv.utilsx.async.Signal;
import org.jetbrains.annotations.NotNull;

public interface Future<T> {

    @NotNull
    Poll<T> poll(Context context);

    default T awaitSync() {
        Signal signal = new Signal();
        Waker waker = new Waker(signal);
        Context context = new Context(waker);
        while (true) {
            Poll<T> poll = poll(context);
            if (poll.isReady()) return poll.get();
            signal.await();
        }
    }
}
