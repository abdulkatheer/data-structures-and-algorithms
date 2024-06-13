package io.abdul;

import io.abdul.api.exception.NotImplemented;

import java.util.Random;

public class RandomizedQuickSort<E extends Comparable<E>> implements Sort<E> {
    private static final Random RANDOM = new Random();

    @Override
    public void sortInPlace(E[] elements) {
        if (elements.length <= 1) {
            return;
        }
        quickSort(elements, 0, elements.length - 1);
    }

    @Override
    public E[] sort(E[] elements) {
        throw new NotImplemented("sort() not implemented in " + RandomizedQuickSort.class.getName());
    }

    private static <E extends Comparable<E>> void quickSort(E[] elements, int start, int end) {
        if (start == end) { // Base case: one element exists
            return;
        }
        if (start > end) { // Base case: invalid segment
            return;
        }
        int partitionIndex = randomizedPartition(elements, start, end);
        quickSort(elements, start, partitionIndex - 1);
        quickSort(elements, partitionIndex + 1, end);
    }

    // Randomized Lomuto Partitioning Algorithm
    public static <E extends Comparable<E>> int randomizedPartition(E[] elements, int start, int end) {
        int randomPivotIndex = RANDOM.nextInt(start, end + 1);
        // Move element at this randomly picked location to the end, so that probability of splitting at the end/start is been reduced
        E temp = elements[randomPivotIndex];
        elements[randomPivotIndex] = elements[end];
        elements[end] = temp;
        return partition(elements, start, end);
    }

    // Lomuto Partitioning Algorithm
    // At the end of this, elements[end] will be set in its position
    public static <E extends Comparable<E>> int partition(E[] elements, int start, int end) {
        E pivot = elements[end];

        int partIndex = start;
        // at any point in time, all elements before partIndex will be lesser than pivot
        // and all elements after partIndex will be greater than pivot
        // So iterate through the list and anytime you find a smaller element put it in the place of partIndex and move partIndex to next location
        for (int i = start; i < end; i++) {
            if (elements[i].compareTo(pivot) <= 0) {
                E temp = elements[i];
                elements[i] = elements[partIndex];
                elements[partIndex] = temp;
                partIndex++;
            }
        }

        if (partIndex != end) { // last element is not in its sorted position, do it
            elements[end] = elements[partIndex];
            elements[partIndex] = pivot;
        }
        return partIndex;
    }
}
