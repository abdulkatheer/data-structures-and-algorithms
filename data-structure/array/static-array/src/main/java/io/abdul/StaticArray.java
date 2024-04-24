package io.abdul;

public class StaticArray<E> {
    private final Object[] elements;

    public StaticArray(int size) {
        this.elements = new Object[size];
    }

    // O(1)
    public E addAtPosition(int index, E element) {
        validateIndex(index);
        Object preElement = elements[index];
        elements[index] = element;
        return (E) preElement;
    }

    // O(n)
    public void insertAtPosition(int index, E element) {
        validateIndex(index);

        Object temp = elements[index];
        elements[index] = element;
        for (int i = index + 1; i < elements.length; i++) {
            Object t = elements[i];
            elements[i] = temp;
            temp = t;
        }
    }

    // O(n)
    public E removeAtPosition(int index) {
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
    public E findAtIndex(int index) {
        validateIndex(index);
        return (E) elements[index];
    }

    // O(n)
    public int search(E element) {
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] != null && elements[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }


    private void validateIndex(int index) {
        if (index < 0 || index >= elements.length) {
            throw new IllegalArgumentException("Invalid index");
        }
    }

}
