package io.abdul;

import io.abdul.api.Array;
import io.abdul.api.exception.NotImplemented;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

public class DynamicArray<E> implements Array<E> {
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
    public static final int SOFT_MAX_ARRAY_LENGTH = Integer.MAX_VALUE - 8;
    private static final int DEFAULT_CAPACITY = 10;

    private Object[] elements;
    private int size = 0;

    public DynamicArray(int initialCapacity) {
        elements = new Object[initialCapacity];
    }

    public DynamicArray() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    // O(n)
    @Override
    public int add(E element) {
        if (size + 1 >= elements.length) {
            grow(size + 1);
        }
        elements[size] = element;
        size++;
        return size - 1;
    }

    // O(1)
    @Override
    public E set(int position, E element) {
        validateIndex(position);
        Object preElement = elements[position];
        elements[position] = element;
        return (E) preElement;
    }

    // O(n)
    @Override
    public void insert(int position, E element) {
        validateIndex(position);

        Object temp = elements[position];
        elements[position] = element;

        if (size + 1 >= elements.length) {
            grow(size + 1);
        }
        for (int i = position + 1; i <= size; i++) {
            Object t = elements[i];
            elements[i] = temp;
            temp = t;
        }
        size++;
    }

    // O(n)
    @Override
    public E remove(int position) {
        validateIndex(position);
        E element = (E) elements[position];
        elements[position] = null;

        for (int i = position; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }

        elements[size - 1] = null;
        size--;
        return element;
    }

    @Override
    public Optional<Integer> lookup(E element) {
        for (int i = 0; i < size; i++) {
            if (elements[i] != null && elements[i].equals(element)) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    // O(1)
    @Override
    public E get(int index) {
        validateIndex(index);
        return (E) elements[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        throw new NotImplemented("iterator not implemented in Dynamic Array");
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Invalid index");
        }
    }

    private void grow(int minCapacity) {
        int oldCapacity = elements.length;
        if (oldCapacity > 0 || elements != DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            int newCapacity = newLength(oldCapacity,
                    minCapacity - oldCapacity, /* minimum growth */
                    oldCapacity >> 1           /* preferred growth */);
            elements = Arrays.copyOf(elements, newCapacity);
        } else {
            elements = new Object[Math.max(DEFAULT_CAPACITY, minCapacity)];
        }
    }

    private static int newLength(int oldLength, int minGrowth, int prefGrowth) {
        // preconditions not checked because of inlining
        // assert oldLength >= 0
        // assert minGrowth > 0

        int prefLength = oldLength + Math.max(minGrowth, prefGrowth); // might overflow
        if (0 < prefLength && prefLength <= SOFT_MAX_ARRAY_LENGTH) {
            return prefLength;
        } else {
            // put code cold in a separate method
            return hugeLength(oldLength, minGrowth);
        }
    }

    private static int hugeLength(int oldLength, int minGrowth) {
        int minLength = oldLength + minGrowth;
        if (minLength < 0) { // overflow
            throw new OutOfMemoryError(
                    "Required array length " + oldLength + " + " + minGrowth + " is too large");
        } else if (minLength <= SOFT_MAX_ARRAY_LENGTH) {
            return SOFT_MAX_ARRAY_LENGTH;
        } else {
            return minLength;
        }
    }
}
