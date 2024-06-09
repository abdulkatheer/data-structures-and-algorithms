package io.abdul;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SelectionSortTest {

    @Test
    void sortInPlace() {
        SelectionSort<Integer> sort = new SelectionSort<>();
        Integer[] elements = {3, 44, 1, 3, 4, 55, 25, 7, 90, 18};
        sort.sortInPlace(elements);
        assertArrayEquals(new Integer[]{1, 3, 3, 4, 7, 18, 25, 44, 55, 90}, elements);
    }
}