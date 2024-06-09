package io.abdul;

import io.abdul.api.exception.NotImplemented;

public class MergeSort<E extends Comparable<E>> implements Sort<E> {
    @Override
    public void sortInPlace(E[] elements) {
        if (elements.length <= 1) {
            return;
        }
        mergeSort(elements, 0, elements.length - 1);
    }

    @Override
    public E[] sort(E[] elements) {
        throw new NotImplemented("sort() not implemented in " + BubbleSort.class.getName());
    }

    /**
     * Size = 10
     * START
     * (1) 0-9 -> 0-4, 5-9
     * (2) 0-4 -> 0-2, 3-4
     * (2) 0-2 -> 0-1, 2
     * (3) 0-1 -> 0, 1
     * 0-1 merged
     * 0-1 & 2 merged
     * (4) 3-4 -> 3, 4
     * 3-4 merged
     * 0-2 & 3-4 & merged
     * <p>
     * (5) 5-9 -> 5-7, 8-9
     * (6) 5-7 -> 5-6, 7
     * (7) 5-6 -> 5, 6
     * 5 & 6 merged
     * 5-6 & 7 merged
     * (8) 8-9 -> 8, 9
     * 8 & 9 merged
     * 5-7 & 8-9 merged
     * <p>
     * 0-4 & 5-9 merged
     * END
     *
     * @param elements
     * @param start
     * @param end
     * @param <E>
     */
    private static <E extends Comparable<E>> void mergeSort(E[] elements, int start, int end) {
        if (start == end) { // Base case: only one element in the array
            return;
        }
        System.out.println(start + "-" + end);
        int mid = (start + end) / 2;
        mergeSort(elements, start, mid);
        mergeSort(elements, mid + 1, end);
        merge(elements, start, mid, end);
    }

    private static <E extends Comparable<E>> void merge(E[] elements, int start, int mid, int end) {
        // Take a copy of the two lists
        Object[] copy = new Object[elements.length];
        for (int i = start; i < end + 1; i++) {
            copy[i] = elements[i];
        }

        // merge
        int i = start; // first array index pointer
        int j = mid + 1; // second array index pointer
        int k = start; // copy pointer

        while (i <= mid && j <= end) { // while both lists have elements to compare
            if (((E) copy[i]).compareTo(((E) copy[j])) <= 0) {
                elements[k] = (E) copy[i];
                i++;
            } else {
                elements[k] = (E) copy[j];
                j++;
            }
            k++;
        }

        // in case of odd number of elements, left array will have element
        /**
         * size = 10
         * 0 + 9 / 2 = 4 | 0-4, 5-9
         * 5 + 9 / 2 = 7 | 5-7, 8-9
         * 5 + 7 / 2 = 6 | 5-6, 7
         * 5 + 6 / 2 = 5 | 5, 6
         * 5 & 6 merged
         * 5,6 & 7 merged
         * 5,6,7 * 8,9 merged
         * See left part is holding the odd elements
         */
        while (i <= mid) {
            elements[k] = (E) copy[i];
            k++;
            i++;
        }
    }
}
