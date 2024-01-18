package dev.mv.utilsx.async;

import dev.mv.utilsx.generic.Pair;

import java.util.ArrayList;
import java.util.List;

public class MonoThreadAsyncProcessor extends Thread {

    private final Signal signal = new Signal();
    private List<Pair<AsyncRunnable, Pair<Runnable, AsyncEvent>>> runnables = new ArrayList<>();
    private boolean instant = false;

    public int countTasks() {
        return runnables.size();
    }

    public boolean isBusy() {
        return !runnables.isEmpty();
    }

    public void postRunnable(AsyncRunnable runnable) {
        var next = runnable.next();
        if (next == null) {
            return;
        }
        runnables.add(new Pair<>(runnable, next));
        signal.wake();
    }

    private synchronized void process() {
        while (true) {
            instant = false;
            if (runnables.isEmpty()) {
                waitForSignal();
            }

            List<Pair<AsyncRunnable, Pair<Runnable, AsyncEvent>>> newRunnables = new ArrayList<>();

            for (var next : runnables) {
                if (next.b.a != null) {
                    runTask(next, newRunnables);
                }
                else if (next.b.b != null && next.b.b.done()) {
                    var newNext = next.a.next();
                    if (newNext != null) {
                        next.b = newNext;
                        runTask(next, newRunnables);
                    }
                }
            }

            runnables = newRunnables;

            if (!instant) {
                waitForSignal();
            }
        }
    }

    private void runTask(Pair<AsyncRunnable, Pair<Runnable, AsyncEvent>> next, List<Pair<AsyncRunnable, Pair<Runnable, AsyncEvent>>> queue) {
        next.b.a.run();
        if (next.b.b != null && next.b.b.done()) {
            instant = true;
            var newNext = next.a.next();
            if (newNext != null) {
                next.b = newNext;
                queue.add(next);
            }
        }
        else {
            next.b.a = null;
            next.b.b.register(signal);
            queue.add(next);
        }
    }

    private synchronized void waitForSignal() {
        try {
            synchronized (signal) {
                signal.wait();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        process();
    }
}
