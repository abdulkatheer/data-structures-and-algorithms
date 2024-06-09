package io.abdul;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class BubbleSortOptimizedTest {

    @Test
    void sortInPlace() {
        BubbleSortOptimized<Integer> sort = new BubbleSortOptimized<>();
        Integer[] elements = {3, 44, 1, 3, 4, 55, 25, 7, 90, 18};
        sort.sortInPlace(elements);
        assertArrayEquals(new Integer[]{1, 3, 3, 4, 7, 18, 25, 44, 55, 90}, elements);
    }

    @Test
    void sortInPlacePartiallySorted() {
        BubbleSortOptimized<Integer> sort = new BubbleSortOptimized<>();
        Integer[] elements = {1, 3, 3, 4, 7, 44, 90, 18, 55, 25};
        sort.sortInPlace(elements);
        assertArrayEquals(new Integer[]{1, 3, 3, 4, 7, 18, 25, 44, 55, 90}, elements);
    }

    @Test
    void sortInPlaceFullySorted() {
        BubbleSortOptimized<Integer> sort = new BubbleSortOptimized<>();
        Integer[] elements = {1, 3, 3, 4, 7, 18, 25, 44, 55, 90};
        sort.sortInPlace(elements);
        assertArrayEquals(new Integer[]{1, 3, 3, 4, 7, 18, 25, 44, 55, 90}, elements);
    }
}
