package dev.mv.utilsx.sequence;

import dev.mv.utilsx.generic.Option;

import java.util.function.Consumer;

public class Split<T> implements Sequence<T> {
    private Sequence<T> parent;
    private int workers;

    public Split(Sequence<T> parent, int workers) {
        this.parent = parent;
        this.workers = workers;
    }

    @Override
    public void forEach(Consumer<? super T> function) {
        for (int i = 0; i < workers; i++) {
            Thread thread = new Thread(() -> parent.forEach(function));
            thread.setName("SequenceWorker-" + i);
            thread.start();
        }
    }

    @Override
    public Option<T> next() {
        return parent.next();
    }
}
