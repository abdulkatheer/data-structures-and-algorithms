package io.abdul;

import io.abdul.api.exception.NotImplemented;

/**
 * Time Complexity - O(n<sup>2</sup>) <br>
 * Space Complexity - O(1)
 *
 * @param <E>
 */
public class SelectionSort<E extends Comparable<E>> implements Sort<E> {
    @Override
    public void sortInPlace(E[] elements) {
        if (elements.length <= 1) {
            return;
        }
        for (int i = 0; i < elements.length; i++) {
            int smallest = i;
            for (int j = i + 1; j < elements.length; j++) {
                if (elements[j].compareTo(elements[smallest]) < 0) {
                    smallest = j;
                }
            }
            if (i != smallest) {
                E temp = elements[i];
                elements[i] = elements[smallest];
                elements[smallest] = temp;
            }
        }
    }

    @Override
    public E[] sort(E[] elements) {
        throw new NotImplemented("sort() not implemented in " + BubbleSort.class.getName());
    }
}
