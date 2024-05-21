package io.abdul;

public interface Sort<E> {
    void sortInPlace(E[] elements);

    E[] sort(E[] elements);
}
