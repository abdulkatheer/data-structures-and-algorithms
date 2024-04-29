package io.abdul.api;

import java.util.Iterator;

public interface LinkedList<E> {
    void insert(int position, E element);

    void insertAtTheEnd(E element);

    void insertAtTheBeginning(E element);

    E remove(int position);

    E removeFirstElement();

    E removeLastElement();

    int lookup(E element);

    Iterator<E> iterator();
}
