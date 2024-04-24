package io.abdul;

import java.util.Arrays;

public class DynamicArray<E> {
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
    public static final int SOFT_MAX_ARRAY_LENGTH = Integer.MAX_VALUE - 8;
    private static final int DEFAULT_CAPACITY = 10;

    private Object[] elements;
    int lastUsedIndex = -1;

    public DynamicArray(int initialCapacity) {
        elements = new Object[initialCapacity];
    }

    public DynamicArray() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    // O(n)
    public int add(E element) {
        lastUsedIndex++;
        if (lastUsedIndex >= elements.length) {
            grow(lastUsedIndex + 1);
        }
        elements[lastUsedIndex] = element;
        return lastUsedIndex;
    }

    // O(1)
    public E set(int index, E element) {
        validateIndex(index);
        Object preElement = elements[index];
        elements[index] = element;
        return (E) preElement;
    }

    // O(n)
    public void insert(int index, E element) {
        validateIndex(index);

        Object temp = elements[index];
        elements[index] = element;

        lastUsedIndex++;
        if (lastUsedIndex >= elements.length) {
            grow(lastUsedIndex + 1);
        }
        for (int i = index + 1; i < lastUsedIndex; i++) {
            Object t = elements[i];
            elements[i] = temp;
            temp = t;
        }
    }

    // O(n)
    public E remove(int index) {
        validateIndex(index);
        E element = (E) elements[index];
        elements[index] = null;

        for (int i = index; i < lastUsedIndex; i++) {
            elements[i] = elements[i + 1];
        }

        elements[lastUsedIndex] = null;
        lastUsedIndex--;
        return element;
    }

    // O(1)
    public E find(int index) {
        validateIndex(index);
        return (E) elements[index];
    }

    private void validateIndex(int index) {
        if (index > lastUsedIndex) {
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
