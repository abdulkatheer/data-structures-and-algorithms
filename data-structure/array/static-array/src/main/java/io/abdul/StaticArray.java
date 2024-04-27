package io.abdul;

import io.abdul.api.Array;
import io.abdul.api.exception.IndexOutOfBounds;
import io.abdul.api.exception.NotImplemented;

import java.util.Iterator;
import java.util.Optional;

public class StaticArray<E> implements Array<E> {
    private final Object[] elements;

    public StaticArray(int size) {
        this.elements = new Object[size];
    }

    // O(1)
    @Override
    public E set(int index, E element) {
        validateIndex(index);
        Object preElement = elements[index];
        elements[index] = element;
        return (E) preElement;
    }

    // O(n)
    @Override
    public void insert(int index, E element) {

    }

    // O(n)
    @Override
    public E remove(int index) {
        validateIndex(index);
        E element = (E) elements[index];
        elements[index] = null;

        for (int i = index; i < elements.length - 1; i++) {
            elements[i] = elements[i + 1];
        }

        elements[elements.length - 1] = null;
        return element;
    }

    // O(1)
    @Override
    public E get(int index) {
        validateIndex(index);
        return (E) elements[index];
    }

    // O(n)
    @Override
    public Optional<Integer> lookup(E element) {
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] != null && elements[i].equals(element)) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    @Override
    public int add(E element) throws IndexOutOfBounds {
        throw new NotImplemented("add not implemented in Static Array");
    }

    @Override
    public Iterator<E> iterator() {
        throw new NotImplemented("iterator not implemented in Static Array");
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= elements.length) {
            throw new IllegalArgumentException("Invalid index");
        }
    }
}
