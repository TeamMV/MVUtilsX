package dev.mv.utilsx.futures;

import dev.mv.utilsx.generic.Null;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Sleep implements Future<Null> {

    private static final AtomicBoolean INITIALIZED = new AtomicBoolean(false);
    private static final Mutex<PriorityQueue<TimerEntry>> QUEUE = new Mutex<>(new PriorityQueue<>(Comparator.comparing(o -> o.when)));
    private static final CondVar SIGNAL = new CondVar(QUEUE);

    private final Duration duration;
    private Instant when;
    private boolean started;

    public Sleep(Duration duration) {
        run();
        this.duration = duration;
    }

    public Sleep(long millis) {
        this(Duration.ofMillis(millis));
    }

    @Override
    public @NotNull Poll<Null> poll(Context context) {
        if (duration.isZero()) return new Poll<>(Null.INSTANCE);
        if (!started) {
            when = Instant.now().plusSeconds(duration.getSeconds()).plusNanos(duration.getNano());
            started = true;
            QUEUE.lock();
            QUEUE.getLocked().add(new TimerEntry(when, context.waker()));
            QUEUE.unlock();
            SIGNAL.signal();
            return new Poll<>();
        }
        if (when.compareTo(Instant.now()) >= 0) {
            return new Poll<>(Null.INSTANCE);
        }
        return new Poll<>();
    }

    private static class TimerEntry {
        private Instant when;
        private Waker waker;

        public TimerEntry(Instant when, Waker waker) {
            this.when = when;
            this.waker = waker;
        }
    }

    private static void run() {
        if (!INITIALIZED.getAndSet(true)) {
            while (true) {
                QUEUE.lock();
                TimerEntry entry = QUEUE.getLocked().peek();
                if (entry == null) {
                    SIGNAL.await();
                }
                else {
                    Instant now = Instant.now();
                    if (now.compareTo(entry.when) >= 0) {
                        QUEUE.getLocked().poll();
                        entry.waker.wake();
                        QUEUE.unlock();
                    }
                    else {
                        Instant time = entry.when.plusSeconds(now.getEpochSecond()).plusNanos(now.getNano());
                        Duration duration = Duration.ofSeconds(time.getEpochSecond(), time.getNano());
                        SIGNAL.await(duration);
                    }
                }
            }
        }
    }
}
