package io.abdul.api;

import java.util.Iterator;

public interface LinkedList<E> {
    void insert(int position, E element);

    void insertAtTheEnd(E element);

    void insertAtTheBeginning(E element);

    void remove(int position);

    void removeFirstElement();

    void removeLastElement();

    void lookup(E element);

    Iterator<E> iterator();
}
