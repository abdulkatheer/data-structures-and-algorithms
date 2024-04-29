package io.abdul.api;

import java.util.Iterator;
import java.util.Optional;

public interface LinkedList<E> {
    void insert(int position, E element);

    void insertAtTheEnd(E element);

    void insertAtTheBeginning(E element);

    E remove(int position);

    E removeFirstElement();

    E removeLastElement();

    E getFirstElement();

    E getLastElement();

    Optional<Integer> lookup(E element);

    int size();

    Iterator<E> iterator();
}
