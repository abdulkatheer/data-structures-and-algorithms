package io.abdul;

import io.abdul.api.exception.NotImplemented;

/**
 * Time Complexity: <br>
 * O(n<sup>2</sup>) - When elements are random <br>
 * \u03A9(n) - When elements are already sorted <br>
 * Space Complexity - O(1)
 * <p>
 * Once all elements are sorted, no swaps will happen.
 * Idea is to sense that no swaps didn't happen in previous iteration and stop further iterations
 *
 * @param <E>
 */
public class BubbleSortOptimized<E extends Comparable<E>> implements Sort<E> {
    @Override
    public void sortInPlace(E[] elements) {
        if (elements.length <= 1) {
            return;
        }
        int count = 0;
        for (int i = 0; i < elements.length; i++) {
            boolean swapped = false;
            for (int j = 0; j < elements.length - i - 1; j++) {
                count++;
                if (elements[j].compareTo(elements[j + 1]) > 0) {
                    E temp = elements[j];
                    elements[j] = elements[j + 1];
                    elements[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
        System.out.println("Total iterations = " + count);
    }

    @Override
    public E[] sort(E[] elements) {
        throw new NotImplemented("sort() not implemented in " + BubbleSortOptimized.class.getName());
    }
}
