package com.github.zi_jing.cuckoolib.util.function;

@FunctionalInterface
public interface Validation<T, U> {
  boolean test(T t, U u);
}
