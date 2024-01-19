package dev.mv.utilsx.sequence.integer;

import dev.mv.utilsx.generic.Option;
import dev.mv.utilsx.sequence.Sequence;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.PrimitiveIterator;
import java.util.function.*;

public interface IntSequence extends Iterable<Integer> {
    IntOption next();

    static <T> IntSequence fromSequence(Sequence<T> sequence, ToIntFunction<T> mapper) {
        return () -> {
            var n = sequence.next();
            if (n.isNone()) return IntOption.none();
            return IntOption.some(mapper.applyAsInt(n.getUnchecked()));
        };
    }

    default <R> IntMap<R> map(IntFunction<R> mapper) {
        return new IntMap<>(this, mapper);
    }

    default IntFilter filter(IntPredicate predicate) {
        return new IntFilter(this, predicate);
    }

    default <R> IntFilterMap<R> filterMap(IntFunction<Option<R>> mapper) {
        return new IntFilterMap<>(this, mapper);
    }

    default IntEnumerate enumerate() {
        return new IntEnumerate(this);
    }

    default IntInspect inspect(IntConsumer inspector) {
        return new IntInspect(this, inspector);
    }

    default IntStepBy stepBy(int step) {
        return new IntStepBy(this, step);
    }

    default IntChunks chunks(int size) {
        return new IntChunks(this, size);
    }

    default IntStepByChunks stepByChunks(int step, int size) {
        return new IntStepByChunks(this, step, size);
    }

    default IntChain chain(IntSequence other) {
        return new IntChain(this, other);
    }

    default IntTake take(long limit) {
        return new IntTake(this, limit);
    }

    default <U> IntZip<U> zip(Sequence<U> other) {
        return new IntZip<>(this, other);
    }

    default IntIntZip zipInt(IntSequence other) {
        return new IntIntZip(this, other);
    }

    default IntWindows windows(int size) {
        return new IntWindows(this, size);
    }

    default IntPeakable peak() {
        return new IntPeakable(this);
    }

    default IntMultiPeak multiPeak() {
        return new IntMultiPeak(this);
    }

    default <R> IntMapWhile<R> mapWhile(IntFunction<Option<R>> mapper) {
        return new IntMapWhile<>(this, mapper);
    }

    default IntTakeWhile takeWhile(IntPredicate test) {
        return new IntTakeWhile(this, test);
    }

    default IntSkip skip(int skip) {
        return new IntSkip(this, skip);
    }

    default IntSkipWhile skipWhile(IntPredicate test) {
        return new IntSkipWhile(this, test);
    }

    default IntPutBack putBack() {
        return new IntPutBack(this);
    }

    default IntPutBackN putBackN() {
        return new IntPutBackN(this);
    }

    default IntCycle cycle() {
        return new IntCycle(this);
    }

    default IntDistinct distinct() {
        return new IntDistinct(this);
    }

    default IntSequence mapToInt(IntUnaryOperator mapper) {
        return () -> {
            var n = next();
            if (n.isNone()) return IntOption.none();
            return IntOption.some(mapper.applyAsInt(n.getUnchecked()));
        };
    }

    @Override
    default void forEach(Consumer<? super Integer> function) {
        var next = next();
        while (next.isSome()) {
            function.accept(next.getUnchecked());
            next = next();
        }
    }

    @NotNull
    @Override
    default Iterator<Integer> iterator() {
        return new PrimitiveIterator.OfInt() {
            IntOption buffer = IntSequence.this.next();

            @Override
            public int nextInt() {
                int value = buffer.getUnchecked();
                buffer = IntSequence.this.next();
                return value;
            }

            @Override
            public boolean hasNext() {
                return buffer.isSome();
            }
        };
    }
}
