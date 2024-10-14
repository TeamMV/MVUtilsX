package dev.mv.utilsx.collection;

import dev.mv.utilsx.sequence.Sequence;

public interface IntoSequence<T> {
    Sequence<T> iter();
    Sequence<T> iterCopied();
}
