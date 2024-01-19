package dev.mv.utilsx.futures;

public class Pending<T> implements Future<T> {
    @Override
    public Poll<T> poll(Context context) {
        return new Poll<>();
    }
}
