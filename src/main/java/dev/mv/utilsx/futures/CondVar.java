package dev.mv.utilsx.futures;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public class CondVar {

    private Condition condition;

    public CondVar(Mutex<?> mutex) {
        condition = mutex.lock.newCondition();
    }

    public void await() {
        try {
            condition.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean await(Duration timeout) {
        try {
            return condition.await(timeout.toNanos(), TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean await(long timeoutMillis) {
        return await(Duration.ofMillis(timeoutMillis));
    }

    public void signal() {
        condition.signal();
    }

    public void signalAll() {
        condition.signalAll();
    }

    private <T> void gen(Mutex<T> mutex) {
        if (condition == null) {
            condition = mutex.lock.newCondition();
        }
    }

}
