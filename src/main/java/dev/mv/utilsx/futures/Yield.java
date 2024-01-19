package dev.mv.utilsx.futures;

import dev.mv.utilsx.generic.Null;
import org.jetbrains.annotations.NotNull;

public class Yield implements Future<Null> {

    private boolean set;

    @Override
    public @NotNull Poll<Null> poll(Context context) {
        if (set) return new Poll<>(Null.INSTANCE);
        set = true;
        context.waker().wake();
        return new Poll<>();
    }
}
