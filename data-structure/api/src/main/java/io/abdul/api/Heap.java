package io.abdul.api;

public interface Heap <E extends Comparable<E>> {
    void insert(E element);

    E peek();

    int size();
}
