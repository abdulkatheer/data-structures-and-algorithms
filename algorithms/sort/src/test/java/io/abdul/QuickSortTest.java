package io.abdul;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class QuickSortTest {

    @Test
    void sortInPlaceOddNumbers() {
        QuickSort<Integer> sort = new QuickSort<>();
        Integer[] elements = {3, 44, 1, 3, 4, 55, 25, 7, 90};
        sort.sortInPlace(elements);
        assertArrayEquals(new Integer[]{1, 3, 3, 4, 7, 25, 44, 55, 90}, elements);
    }

    @Test
    void sortInPlaceEvenNumbers() {
        QuickSort<Integer> sort = new QuickSort<>();
        Integer[] elements = {3, 44, 1, 3, 4, 55, 25, 7, 90, 18};
        sort.sortInPlace(elements);
        assertArrayEquals(new Integer[]{1, 3, 3, 4, 7, 18, 25, 44, 55, 90}, elements);
    }

    @Test
    void partition() {
        // 1,3,3,4,7,18,25,44,55,90
        Integer[] elements = {3, 44, 1, 3, 4, 55, 25, 7, 90, 18};
        int partition = QuickSort.partition(elements, 0, 9);
        assertEquals(18, elements[5]);
        partition = QuickSort.partition(elements, 0, partition - 1);
        assertEquals(7, elements[4]);
        partition = QuickSort.partition(elements, 0, partition - 1);
        assertEquals(4, elements[3]);
        partition = QuickSort.partition(elements, 0, partition - 1);
        assertEquals(3, elements[2]);
        partition = QuickSort.partition(elements, 0, partition - 1);
        assertEquals(3, elements[1]);
//        partition = QuickSort.partition(elements, 0, partition - 1);
        assertEquals(1, elements[0]);

        QuickSort.partition(elements, partition + 1, elements.length - 1);
        assertEquals(90, elements[9]);
        QuickSort.partition(elements, partition + 1, elements.length - 2);
        assertEquals(55, elements[8]);
        QuickSort.partition(elements, partition + 1, elements.length - 3);
        assertEquals(44, elements[7]);
        QuickSort.partition(elements, partition + 1, elements.length - 4);
        assertEquals(25, elements[6]);
    }
}