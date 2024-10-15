package dev.mv.utilsx.collection;

import dev.mv.utilsx.ArrayUtils;
import dev.mv.utilsx.generic.Option;
import dev.mv.utilsx.sequence.Sequence;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.RandomAccess;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public class Vec<T> implements RandomAccess, Iterable<T>, IntoSequence<T> {

    transient T[] elements;
    int length;

    public Vec(T... init) {
        elements = init;
        length = init.length;
    }

    public static <T> Vec<T> withCapacity(int capacity, T... ignore) {
        Vec<T> vec = new Vec<>();
        vec.elements = (T[]) Array.newInstance(ignore.getClass().componentType(), capacity);
        return vec;
    }

    private void grow(int minAmount) {
        if (elements.length < 3) {
            elements = Arrays.copyOf(elements, Math.max(minAmount + elements.length, 5));
            return;
        }
        int initialLength = elements.length;
        int newLength = (int) (initialLength * ArrayUtils.phi);
        while (newLength - initialLength < minAmount) {
            newLength = (int) (newLength * ArrayUtils.phi);
        }
        elements = Arrays.copyOf(elements, newLength);
    }

    public int capacity() {
        return elements.length;
    }

    public int len() {
        return length;
    }

    public boolean isEmpty() {
        return length == 0;
    }

    public void reserve(int additional) {
        if (length + additional > elements.length) grow(additional);
    }

    public void reserveExact(int additional) {
        if (length + additional > elements.length) elements = Arrays.copyOf(elements, length + additional);
    }

    public void shrinkToFit() {
        truncate(length);
    }

    public void truncate(int length) {
        if (length < elements.length) elements = Arrays.copyOf(elements, length);
    }

    public void insert(int index, T element) {
        if (length == elements.length) grow(1);

        if (index < length) {
            System.arraycopy(elements, index, elements, index + 1, length - index);
            length++;
        }
        else if (index > length) {
            reserve(index - length);
            length = index + 1;
        }
        else {
            length++;
        }

        elements[index] = element;
    }

    public T replace(int index, T element) {
        if (index >= length || index < 0) throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for length " + length + "!");
        T t = elements[index];
        elements[index] = element;
        return t;
    }

    public int indexOf(T element) {
        for (int i = 0; i < length; i++) {
            if (element == null) {
                if (elements[i] == null) return i;
                continue;
            }
            if (element.equals(elements[i])) return i;
        }
        return -1;
    }

    public T remove(int index) {
        if (index >= length || index < 0) throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for length " + length + "!");
        T element = elements[index];
        System.arraycopy(elements, index + 1, elements, index, length - index - 1);
        length--;
        return element;
    }

    public T remove(T element) {
        return remove(indexOf(element));
    }

    public void push(T element) {
        if (length == elements.length) grow(1);
        elements[length] = element;
        length++;
    }

    public T pop() {
        if (length == 0) return null;
        length--;
        T element = elements[length];
        elements[length] = null;
        return element;
    }

    public T popFirst() {
        if (length == 0) return null;
        length--;
        T element = elements[0];
        System.arraycopy(elements, 1, elements, 0, length);
        elements[length] = null;
        return element;
    }

    public void clear() {
        elements = Arrays.copyOf(elements, 0);
        length = 0;
    }

    public T get(int index) {
        if (index >= length) throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for length " + length + "!");
        return elements[index];
    }

    public void append(Vec<T> other) {
        reserve(other.length);
        System.arraycopy(other.elements, 0, elements, length, other.length);
        length += other.length;
    }

    public void append(T[] other) {
        reserve(other.length);
        System.arraycopy(other, 0, elements, length, other.length);
        length += other.length;
    }

    public void append(Collection<T> other) {
        reserve(other.size());
        System.arraycopy(other.toArray(Arrays.copyOf(elements, 0)), 0, elements, length, other.size());
        length += other.size();
    }

    public T[] toArray() {
        return Arrays.copyOf(elements, length);
    }

    @Override
    public Sequence<T> iter() {
        return new Iter(this);
    }

    @Override
    public Sequence<T> iterCopied() {
        return new IterCopied<>(toArray());
    }

    public Sequence<T> drain() {
        return drain(0, length);
    }

    public Sequence<T> drain(int start, int end) {
        if (start > end) throw new IndexOutOfBoundsException("Start cannot be bigger than end");
        if (end > length) throw new IndexOutOfBoundsException("End index " + end + " is out of bounds for length " + length + "!");
        T[] drained = Arrays.copyOfRange(elements, start, end);
        System.arraycopy(elements, end, elements, start, length - end);
        Arrays.fill(elements, start + length - end, length, null);
        length -= end - start;
        return new IterCopied<>(drained);
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return iter().iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vec vec) {
            if (length != vec.length) return false;
            for (int i = 0; i < length; i++) {
                if (elements[i] == null) {
                    if (vec.elements[i] == null) continue;
                    return false;
                }
                if (!elements[i].equals(vec.elements[i])) return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public Object clone() {
        Vec<T> clone = new Vec<>(Arrays.copyOf(elements, 0));
        clone.elements = Arrays.copyOf(elements, length);
        clone.length = length;
        return clone;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < length; i++) {
            builder.append(elements[i]);
            if (i + 1 < length) builder.append(", ");
        }
        builder.append("]");
        return builder.toString();
    }

    public String toDebugString() {
        StringBuilder builder = new StringBuilder("Vec {\n\telements: [");
        for (int i = 0; i < elements.length; i++) {
            builder.append(elements[i]);
            if (i + 1 < elements.length) builder.append(", ");
        }
        builder.append("],\n\tcapacity: ");
        builder.append(elements.length);
        builder.append(",\n\tlength: ");
        builder.append(length);
        builder.append("\n}");
        return builder.toString();
    }

    public static class Iter<T> implements Sequence<T> {
        private int index = 0;
        private Vec<T> parent;
        private Iter(Vec<T> parent) {
            this.parent = parent;
        }

        @Override
        public Option<T> next() {
            if (index < parent.length) {
                return Option.some(parent.get(index++));
            }
            return Option.none();
        }
    }

    public static class IterCopied<T> implements Sequence<T> {
        private int index = 0;
        private T[] parent;

        private IterCopied(T[] elements) {
            parent = elements;
        }

        @Override
        public Option<T> next() {
            if (index < parent.length) {
                return Option.some(parent[index++]);
            }
            return Option.none();
        }
    }
}
