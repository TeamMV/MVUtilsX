package dev.mv.utilsx.futures;

import dev.mv.utilsx.generic.Null;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

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

    static Future<Null> yield() {
        return new Yield();
    }

    static Future<Null> sleep(Duration duration) {
        return new Sleep(duration);
    }

    static Future<Null> sleep(long millis) {
        return new Sleep(millis);
    }

    static <T> Future<T> pending() {
        return new Pending<>();
    }

    static <T> Future<T> ready(T value) {
        return new Ready<>(value);
    }
}
