package io.abdul;

import io.abdul.api.exception.NotImplemented;

/**
 * Time Complexity - O(n<sup>2</sup>) <br>
 * Space Complexity - O(1)
 *
 * @param <E>
 */
public class BubbleSort<E extends Comparable<E>> implements Sort<E> {
    @Override
    public void sortInPlace(E[] elements) {
        if (elements.length <= 1) {
            return;
        }
        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements.length - i - 1; j++) {
                if (elements[j].compareTo(elements[j + 1]) > 0) {
                    E temp = elements[j];
                    elements[j] = elements[j + 1];
                    elements[j + 1] = temp;
                }
            }
        }
    }

    @Override
    public E[] sort(E[] elements) {
        throw new NotImplemented("sort() not implemented in " + BubbleSort.class.getName());
    }
}
