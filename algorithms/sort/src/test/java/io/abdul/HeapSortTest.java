package io.abdul;

import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HeapSortTest {

    @Test
    void sortInPlace() {
        sortOneElement();

        sortTwoElements();

        sortThreeElements();

        sortFourElements();

        sortFiveElements();
    }

    @Test
    void sortInPlaceWithComparator() {
        sortOneElementWithComparator();

        sortTwoElementsWithComparator();

        sortThreeElementsWithComparator();

        sortFourElementsWithComparator();

        sortFiveElementsWithComparator();
    }

    private static void sortFiveElementsWithComparator() {
        HeapSort<Integer> heapSort = new HeapSort<Integer>(Comparator.reverseOrder());

        Integer[] numbers = new Integer[]{1, 2, 3, 4, 5};
        heapSort.sortInPlace(numbers);
        assertEquals(5, numbers[0]);
        assertEquals(4, numbers[1]);
        assertEquals(3, numbers[2]);
        assertEquals(2, numbers[3]);
        assertEquals(1, numbers[4]);
    }

    private static void sortFourElementsWithComparator() {
        HeapSort<Integer> heapSort = new HeapSort<Integer>(Comparator.reverseOrder());

        Integer[] numbers = new Integer[]{1, 2, 3, 4};
        heapSort.sortInPlace(numbers);
        assertEquals(4, numbers[0]);
        assertEquals(3, numbers[1]);
        assertEquals(2, numbers[2]);
        assertEquals(1, numbers[3]);
    }

    private static void sortThreeElementsWithComparator() {
        HeapSort<Integer> heapSort = new HeapSort<Integer>(Comparator.reverseOrder());

        Integer[] numbers = new Integer[]{1, 2, 3};
        heapSort.sortInPlace(numbers);
        assertEquals(3, numbers[0]);
        assertEquals(2, numbers[1]);
        assertEquals(1, numbers[2]);
    }

    private static void sortTwoElementsWithComparator() {
        HeapSort<Integer> heapSort = new HeapSort<Integer>(Comparator.reverseOrder());

        Integer[] numbers = new Integer[]{1, 2};
        heapSort.sortInPlace(numbers);
        assertEquals(2, numbers[0]);
        assertEquals(1, numbers[1]);
    }

    private static void sortOneElementWithComparator() {
        HeapSort<Integer> heapSort = new HeapSort<Integer>(Comparator.reverseOrder());

        Integer[] numbers = new Integer[]{1};
        heapSort.sortInPlace(numbers);
        assertEquals(1, numbers[0]);
    }

    private static void sortFiveElements() {
        HeapSort<Integer> heapSort = new HeapSort<>();

        Integer[] numbers = new Integer[]{5, 4, 3, 2, 1};
        heapSort.sortInPlace(numbers);
        assertEquals(1, numbers[0]);
        assertEquals(2, numbers[1]);
        assertEquals(3, numbers[2]);
        assertEquals(4, numbers[3]);
        assertEquals(5, numbers[4]);
    }

    private static void sortFourElements() {
        HeapSort<Integer> heapSort = new HeapSort<>();

        Integer[] numbers = new Integer[]{4, 3, 2, 1};
        heapSort.sortInPlace(numbers);
        assertEquals(1, numbers[0]);
        assertEquals(2, numbers[1]);
        assertEquals(3, numbers[2]);
        assertEquals(4, numbers[3]);
    }

    private static void sortThreeElements() {
        HeapSort<Integer> heapSort = new HeapSort<>();

        Integer[] numbers = new Integer[]{3, 2, 1};
        heapSort.sortInPlace(numbers);
        assertEquals(1, numbers[0]);
        assertEquals(2, numbers[1]);
        assertEquals(3, numbers[2]);
    }

    private static void sortTwoElements() {
        HeapSort<Integer> heapSort = new HeapSort<>();

        Integer[] numbers = new Integer[]{2, 1};
        heapSort.sortInPlace(numbers);
        assertEquals(1, numbers[0]);
        assertEquals(2, numbers[1]);
    }

    private static void sortOneElement() {
        HeapSort<Integer> heapSort = new HeapSort<>();

        Integer[] numbers = new Integer[]{1};
        heapSort.sortInPlace(numbers);
        assertEquals(1, numbers[0]);
    }
}