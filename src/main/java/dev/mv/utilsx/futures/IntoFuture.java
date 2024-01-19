package dev.mv.utilsx.futures;

import java.util.function.Supplier;

@FunctionalInterface
public interface IntoFuture<T> extends Supplier<Future<T>> {}
