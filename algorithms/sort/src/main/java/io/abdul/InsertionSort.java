package io.abdul;

import io.abdul.api.exception.NotImplemented;

/**
 * Time Complexity: <br>
 * O(n<sup>2</sup>) - When elements are random <br>
 * \u03A9(n) - When elements are already sorted <br>
 * Space Complexity - O(1)
 * @param <E>
 */
public class InsertionSort<E extends Comparable<E>> implements Sort<E> {
    @Override
    public void sortInPlace(E[] elements) {
        if (elements.length <= 1) {
            return;
        }
        for (int i = 1; i < elements.length; i++) {
            int position = i;
            E toBeInserted = elements[i];
            for (int j = i - 1; j >= 0; j--) {
                // 1 3 4 2
                // 1 3 4 4
                // 1 3 3 4
                if (elements[j].compareTo(toBeInserted) <= 0) {
                    break;
                }
                elements[j + 1] = elements[j]; // making room for insertion
                position = j;
            }
            if (position != i) {
                elements[position] = toBeInserted; //  insert in the space made
            }
        }
    }

    @Override
    public E[] sort(E[] elements) {
        throw new NotImplemented("sort() not implemented in " + BubbleSort.class.getName());
    }
}
