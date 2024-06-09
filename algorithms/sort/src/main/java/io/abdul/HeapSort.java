package io.abdul;

import io.abdul.api.exception.NotImplemented;

import java.util.Comparator;

/**
 * Time Complexity - O(n * log n) <br>
 * Space Complexity - O(1)
 *
 * @param <E>
 */
public class HeapSort<E extends Comparable<E>> implements Sort<E> {
    private final Comparator<E> comparator;

    public HeapSort() {
        this.comparator = null;
    }

    public HeapSort(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public void sortInPlace(E[] elements) {
        if (elements.length <= 1) {
            return;
        }
        if (comparator != null) {
            heapSort(elements, comparator);
        } else {
            heapSort(elements);
        }
    }

    @Override
    public E[] sort(E[] elements) {
        throw new NotImplemented("sort() not implemented yet!");
    }

    /**
     * Time Complexity  : O(n log n)
     * Space Complexity : O(1)
     *
     * @param elements
     * @param comparator
     * @param <E>
     */
    private static <E extends Comparable<E>> void heapSort(E[] elements, Comparator<E> comparator) {
        buildHeap(elements, comparator);
        int size = elements.length;
        int rootPos = 1;
        for (int pos = elements.length; pos >= 2; pos--) {
            E temp = elements[pos - 1];
            elements[pos - 1] = elements[rootPos - 1]; // moving next bigger/smaller element to the end
            elements[rootPos - 1] = temp; // moving last element to the front
            size--; // reducing heap size, which ignores last sorted element
            heapify(elements, comparator, size, rootPos);
        }
    }

    /**
     * Time Complexity  : O(n)
     * Space Complexity : O(1)
     *
     * @param elements
     * @param comparator
     * @param <E>
     */
    private static <E extends Comparable<E>> void buildHeap(E[] elements, Comparator<E> comparator) {
        int lastParent = elements.length / 2;
        for (int i = lastParent; i >= 1; i--) {
            heapify(elements, comparator, elements.length, i);
        }
    }

    /**
     * Time Complexity  : O(log n)
     * Space Complexity : O(1)
     * Top-bottom approach
     * In-place heapify
     * O(log n)
     */
    private static <E extends Comparable<E>> void heapify(E[] elements, Comparator<E> comparator, int heapSize, int position) {
        int leftChildPosition = 2 * position;
        int rightChildPosition = 2 * position + 1;

        int toBeReplaced = position;

        if (leftChildPosition <= heapSize && comparator.compare(elements[leftChildPosition - 1], elements[toBeReplaced - 1]) > 0) { // if left is bigger
            toBeReplaced = leftChildPosition;
        }

        if (rightChildPosition <= heapSize && comparator.compare(elements[rightChildPosition - 1], elements[toBeReplaced - 1]) > 0) { // if right is bigger
            toBeReplaced = rightChildPosition;
        }

        if (toBeReplaced != position) { // need to swap and heapify
            E temp = elements[position - 1];
            elements[position - 1] = elements[toBeReplaced - 1];
            elements[toBeReplaced - 1] = temp;
            heapify(elements, comparator, heapSize, toBeReplaced); // Recursive case
        }
        // base case - silently terminates
    }

    /**
     * Time Complexity  : O(n log n)
     * Space Complexity : O(1)
     *
     * @param elements
     * @param <E>
     */
    private static <E extends Comparable<E>> void heapSort(E[] elements) {
        buildHeap(elements);
        int size = elements.length;
        int rootPos = 1;
        for (int pos = elements.length; pos >= 2; pos--) {
            E temp = elements[pos - 1];
            elements[pos - 1] = elements[rootPos - 1]; // moving next bigger/smaller element to the end
            elements[rootPos - 1] = temp; // moving last element to the front
            size--; // reducing heap size, which ignores last sorted element
            heapify(elements, size, rootPos);
        }
    }

    /**
     * Time Complexity  : O(n)
     * Space Complexity : O(1)
     *
     * @param elements
     * @param <E>
     */
    private static <E extends Comparable<E>> void buildHeap(E[] elements) {
        int lastParent = elements.length / 2;
        for (int i = lastParent; i >= 1; i--) {
            heapify(elements, elements.length, i);
        }
    }

    /**
     * Time Complexity  : O(log n)
     * Space Complexity : O(1)
     * Top-bottom approach
     * In-place heapify
     * O(log n)
     */
    private static <E extends Comparable<E>> void heapify(E[] elements, int heapSize, int position) {
        int leftChildPosition = 2 * position;
        int rightChildPosition = 2 * position + 1;

        int toBeReplaced = position;
        if (leftChildPosition <= heapSize && elements[leftChildPosition - 1].compareTo(elements[toBeReplaced - 1]) > 0) { // if left is bigger
            toBeReplaced = leftChildPosition;
        }
        if (rightChildPosition <= heapSize && elements[rightChildPosition - 1].compareTo(elements[toBeReplaced - 1]) > 0) { // if right is bigger
            toBeReplaced = rightChildPosition;
        }

        if (toBeReplaced != position) { // need to swap and heapify
            E temp = elements[position - 1];
            elements[position - 1] = elements[toBeReplaced - 1];
            elements[toBeReplaced - 1] = temp;
            heapify(elements, heapSize, toBeReplaced); // Recursive case
        }
        // base case - silently terminates
    }

}
