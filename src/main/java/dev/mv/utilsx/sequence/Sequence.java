package dev.mv.utilsx.sequence;

import dev.mv.utilsx.collection.Vec;
import dev.mv.utilsx.generic.Option;
import dev.mv.utilsx.generic.Pair;
import dev.mv.utilsx.sequence.integer.IntOption;
import dev.mv.utilsx.sequence.integer.IntSequence;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.*;

@SuppressWarnings("unchecked")
@FunctionalInterface
public interface Sequence<T> extends Iterable<T> {

    @NotNull
    Option<T> next();

    @Override
    default Iterator<T> iterator() {
        return new Iterator<T>() {
            Option<T> buffer = Sequence.this.next();

            @Override
            public boolean hasNext() {
                return buffer.isSome();
            }

            @Override
            public T next() {
                T value = buffer.getUnchecked();
                buffer = Sequence.this.next();
                return value;
            }
        };
    }

    default <R> Map<T, R> map(Function<T, R> mapper) {
        return new Map<>(this, mapper);
    }

    default Filter<T> filter(Predicate<T> predicate) {
        return new Filter<>(this, predicate);
    }

    default <R> FilterMap<T, R> filterMap(Function<T, Option<R>> mapper) {
        return new FilterMap<>(this, mapper);
    }

    default Enumerate<T> enumerate() {
        return new Enumerate<>(this);
    }

    default Inspect<T> inspect(Consumer<T> inspector) {
        return new Inspect<>(this, inspector);
    }

    default StepBy<T> stepBy(int step) {
        return new StepBy<>(this, step);
    }

    default Chunks<T> chunks(int size) {
        return new Chunks<>(this, size);
    }

    default StepByChunks<T> stepByChunks(int step, int size) {
        return new StepByChunks<>(this, step, size);
    }

    default Chain<T> chain(Sequence<T> other) {
        return new Chain<>(this, other);
    }

    default Take<T> take(long limit) {
        return new Take<>(this, limit);
    }

    default <U> Zip<T, U> zip(Sequence<U> other) {
        return new Zip<>(this, other);
    }

    default Windows<T> windows(int windowSize) {
        return new Windows<>(this, windowSize);
    }

    default Peekable<T> peekable() {
        return new Peekable<>(this);
    }

    default MultiPeek<T> multiPeek() {
        return new MultiPeek<>(this);
    }

    default <R> MapWhile<T, R> mapWile(Function<T, Option<R>> mapper) {
        return new MapWhile<>(this, mapper);
    }

    default TakeWhile<T> takeWhile(Predicate<T> predicate) {
        return new TakeWhile<>(this, predicate);
    }

    default Skip<T> skip(int skip) {
        return new Skip<>(this, skip);
    }

    default SkipWhile<T> skipWhile(Predicate<T> test) {
        return new SkipWhile<>(this, test);
    }

    default PutBack<T> putBack() {
        return new PutBack<>(this);
    }

    default PutBackN<T> putBackN() {
        return new PutBackN<>(this);
    }

    default Cycle<T> cycle() {
        return new Cycle<>(this);
    }

    default Distinct<T> distinct() {
        return new Distinct<>(this);
    }

    default Split<T> split(int workers) {
        return new Split<>(this, workers);
    }

    default IntSequence mapToInt(ToIntFunction<T> mapper) {
        return IntSequence.fromSequence(this, mapper);
    }

    @Override
    default void forEach(Consumer<? super T> function) {
        var next = next();
        while (next.isSome()) {
            function.accept(next.getUnchecked());
            next = next();
        }
    }

    default Option<T> reduce(BinaryOperator<T> accumulator) {
        var first = next();
        if (first.isSome()) {
            T combined = first.getUnchecked();
            var e = next();
            if (e.isNone()) return first;
            T t = e.getUnchecked();
            while (e.isSome()) {
                e = next();
                if (e.isSome())
                    t = e.getUnchecked();{
                    combined = accumulator.apply(combined, t);
                }
            }
            return Option.some(combined);
        }
        return Option.none();
    }

    default <U> Option<U> fold(U start, BiFunction<U, T, U> accumulator) {
        U combined = start;
        var e = next();
        if (e.isNone()) return Option.some(start);
        T t = e.getUnchecked();
        combined = accumulator.apply(combined, t);
        while (e.isSome()) {
            e = next();
            if (e.isSome()) {
                t = e.getUnchecked();
                combined = accumulator.apply(combined, t);
            }
        }
        return Option.some(combined);
    }

    default boolean all(Predicate<T> test) {
        var n = next();
        while (n.isSome()) {
            if(!test.test(n.getUnchecked())) return false;
            n = next();
        }
        return true;
    }

    default boolean any(Predicate<T> test) {
        var n = next();
        while (n.isSome()) {
            if(test.test(n.getUnchecked())) return true;
            n = next();
        }
        return false;
    }

    default Option<T> find(Predicate<T> test) {
        return filter(test).next();
    }

    default Option<T> last() {
        var n = next();
        if (n.isNone()) return Option.none();
        var prev = n;
        n = next();
        while (n.isSome()) {
            prev = n;
            n = next();
        }
        return prev;
    }

    default Option<T> nth(int n) {
        return skip(n).next();
    }

    default int count() {
        int count = 0;
        while (next().isSome()) count++;
        return count;
    }

    default void print() {
        forEach(System.out::println);
    }

    default <B> B collect(B... ignore) {
        Class<?> clazz = ignore.getClass().componentType();

        if (clazz.equals(List.class) || clazz.equals(ArrayList.class)) {
            List<T> list = new ArrayList<>();
            forEach(list::add);
            return (B) list;
        }
        else if (clazz.isArray()) {
            Vec<T> vec = new Vec<>();
            forEach(vec::push);
            return (B) vec.toArray();
        }
        else if (clazz.equals(Vec.class)) {
            Vec<T> vec = new Vec<>();
            forEach(vec::push);
            return (B) vec;
        }
        else if (clazz.equals(Set.class) || clazz.equals(HashSet.class)) {
            Set<T> set = new HashSet<>();
            forEach(set::add);
            return (B) set;
        }
        else if (clazz.equals(java.util.Map.class) || clazz.equals(HashMap.class)) {
            java.util.Map<Object, Object> map = new HashMap<>();
            for (T value : this) {
                Pair<Object, Object> pair = (Pair<Object, Object>) value;
                map.put(pair.a, pair.b);
            }
            return (B) map;
        }
        else if (clazz.equals(java.lang.CharSequence.class) || clazz.equals(String.class)) {
            StringBuilder builder = new StringBuilder();
            for (T value : this) builder.append(value);
            return (B) builder.toString();
        }

        throw new RuntimeException("Class " + clazz.getName() + " is not collectable from sequence.");
    }

    static <T> Sequence<T> once(T value) {
        return new Sequence<T>() {
            boolean used = false;

            @Override
            public Option<T> next() {
                if (!used) {
                    used = true;
                    return Option.some(value);
                }
                return Option.none();
            }
        };
    }

    static <T> Sequence<T> repeating(T value) {
        return () -> Option.some(value);
    }

    static <T> Sequence<T> repeating(Supplier<T> supplier) {
        return () -> Option.some(supplier.get());
    }

    static <T> Sequence<T> from(T... values) {
        return new Sequence<T>() {
            private int i;

            @Override
            public Option<T> next() {
                if (i >= values.length) {
                    return Option.none();
                }
                return Option.some(values[i++]);
            }
        };
    }

    static <T> Sequence<T> from(Iterable<T> iterable) {
        return Sequence.from(iterable.iterator());
    }

    static <T> Sequence<T> from(Iterator<T> iterator) {
        return () -> {
            if (iterator.hasNext()) return Option.some(iterator.next());
            return Option.none();
        };
    }

    static Sequence<Integer> counter() {
        return new Sequence<>() {
            int i = 0;
            @Override
            public Option<Integer> next() {
                return Option.some(i++);
            }
        };
    }

    static Randoms random() {
        return new Randoms(new Random());
    }

    static Randoms random(Random random) {
        return new Randoms(random);
    }

    class Randoms {
        private Random random;
        private double lowerBound = 0, upperBound = 1;
        private boolean bound = false;

        private Randoms(Random random) {
            this.random = random;
        }

        public Randoms bind(double lower, double upper) {
            if (lower >= upper) {
                throw new IllegalStateException("Lower bound " + lower + " is bigger than upper bound" + upper);
            }
            lowerBound = lower;
            upperBound = upper;
            bound = true;

            return this;
        }

        public IntSequence ints() {
            if (!bound) {
                return () -> IntOption.some(random.nextInt());
            }
            int lower = (int) this.lowerBound;
            int upper = (int) this.upperBound;
            return () -> IntOption.some(random.nextInt(upper - lower) + lower);
        }

        public Sequence<Float> floats() {
                if (!bound) {
                    return () -> Option.some(random.nextFloat());
                }
                float lower = (float) this.lowerBound;
                float upper = (float) this.upperBound;
                return () -> Option.some(random.nextFloat(upper - lower) + lower);
        }

        public Sequence<Long> longs() {
                if (!bound) {
                    return () -> Option.some(random.nextLong());
                }
                long lower = Double.doubleToLongBits(this.lowerBound);
                long upper = Double.doubleToLongBits(this.upperBound);
                return () -> Option.some(random.nextLong(upper - lower) + lower);
        }

        public Sequence<Double> doubles() {
                if (!bound) {
                    return () -> Option.some(random.nextDouble());
                }
                return () -> Option.some(random.nextDouble(upperBound - lowerBound) + lowerBound);
        }
    }
}