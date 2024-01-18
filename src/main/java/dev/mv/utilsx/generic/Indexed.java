package dev.mv.utilsx.generic;

public class Indexed<T> {
    public int i;
    public T value;

    public Indexed(int i, T value) {
        this.i = i;
        this.value = value;
    }

    public Pair<Integer, T> asPair() {
        return new Pair<>(i, value);
    }
}
