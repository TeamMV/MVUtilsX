package dev.mv.utilsx.futures;

public class Ready<T> implements Future<T> {

    private T value;

    public Ready(T value) {
        this.value = value;
    }

    @Override
    public Poll<T> poll(Context context) {
        return new Poll<>(value);
    }
}
