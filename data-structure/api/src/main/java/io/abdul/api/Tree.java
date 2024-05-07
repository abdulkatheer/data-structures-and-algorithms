package io.abdul.api;

import java.util.List;
import java.util.Optional;

public interface Tree<E> {
    void insert(E element);

    Optional<E> lookup(E element);

    boolean remove(E element);

    void empty();

    List<E> getElements();

    int size();
}
