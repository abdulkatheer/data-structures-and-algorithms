package io.abdul;

import io.abdul.api.Heap;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HeapUsingDynamicArrayTest {

    @Test
    void insert() {
        insertOneElement();

        insertTwoElements();

        insertThreeElements();

        insertFourElements();

        insertFiveElements();

        insertSixElements();
    }

    @Test
    void insertWithComparator() {
        insertOneElementWithComparator();

        insertTwoElementsWithComparator();

        insertThreeElementsWithComparator();

        insertFourElementsWithComparator();

        insertFiveElementsWithComparator();

        insertSixElementsWithComparator();
    }


    @Test
    void delete() {
        withOneElement();

        withTwoElements();

        withThreeElements();

        withFourElements();

        withFiveElements();

        withSixElements();
    }

    @Test
    void deleteWithComparator() {
        withOneElementAndComparator();

        withTwoElementsAndComparator();

        withThreeElementsAndComparator();

        withFourElementsAndComparator();

        withFiveElementsAndComparator();

        withSixElementsAndComparator();
    }

    private static void insertSixElementsWithComparator() {
        Heap<Integer> numbers = new HeapUsingDynamicArray<Integer>(Comparator.reverseOrder());
        numbers.insert(6);
        numbers.insert(5);
        numbers.insert(4);
        numbers.insert(3);
        numbers.insert(2);
        numbers.insert(1);

        assertEquals(6, numbers.size());
        assertEquals(1, numbers.delete());

        assertEquals(5, numbers.size());
        assertEquals(2, numbers.delete());

        assertEquals(4, numbers.size());
        assertEquals(3, numbers.delete());

        assertEquals(3, numbers.size());
        assertEquals(4, numbers.delete());

        assertEquals(2, numbers.size());
        assertEquals(5, numbers.delete());

        assertEquals(1, numbers.size());
        assertEquals(6, numbers.delete());

        assertEquals(0, numbers.size());
    }

    private static void insertFiveElementsWithComparator() {
        Heap<Integer> numbers = new HeapUsingDynamicArray<Integer>(Comparator.reverseOrder());
        numbers.insert(5);
        numbers.insert(4);
        numbers.insert(3);
        numbers.insert(2);
        numbers.insert(1);

        assertEquals(5, numbers.size());
        assertEquals(1, numbers.delete());

        assertEquals(4, numbers.size());
        assertEquals(2, numbers.delete());

        assertEquals(3, numbers.size());
        assertEquals(3, numbers.delete());

        assertEquals(2, numbers.size());
        assertEquals(4, numbers.delete());

        assertEquals(1, numbers.size());
        assertEquals(5, numbers.delete());

        assertEquals(0, numbers.size());
    }

    private static void insertFourElementsWithComparator() {
        Heap<Integer> numbers = new HeapUsingDynamicArray<Integer>(Comparator.reverseOrder());
        numbers.insert(4);
        numbers.insert(3);
        numbers.insert(2);
        numbers.insert(1);

        assertEquals(4, numbers.size());
        assertEquals(1, numbers.delete());

        assertEquals(3, numbers.size());
        assertEquals(2, numbers.delete());

        assertEquals(2, numbers.size());
        assertEquals(3, numbers.delete());

        assertEquals(1, numbers.size());
        assertEquals(4, numbers.delete());

        assertEquals(0, numbers.size());
    }

    private static void insertThreeElementsWithComparator() {
        Heap<Integer> numbers = new HeapUsingDynamicArray<Integer>(Comparator.reverseOrder());
        numbers.insert(3);
        numbers.insert(2);
        numbers.insert(1);

        assertEquals(3, numbers.size());
        assertEquals(1, numbers.delete());

        assertEquals(2, numbers.size());
        assertEquals(2, numbers.delete());

        assertEquals(1, numbers.size());
        assertEquals(3, numbers.delete());

        assertEquals(0, numbers.size());
    }

    private static void insertTwoElementsWithComparator() {
        Heap<Integer> numbers = new HeapUsingDynamicArray<Integer>(Comparator.reverseOrder());
        numbers.insert(1);
        numbers.insert(2);

        assertEquals(2, numbers.size());
        assertEquals(1, numbers.delete());

        assertEquals(1, numbers.size());
        assertEquals(2, numbers.delete());

        assertEquals(0, numbers.size());
    }

    private static void insertOneElementWithComparator() {
        Heap<Integer> numbers = new HeapUsingDynamicArray<Integer>(Comparator.reverseOrder());
        numbers.insert(1);

        assertEquals(1, numbers.size());
        assertEquals(1, numbers.delete());

        assertEquals(0, numbers.size());
    }


    private static void insertSixElements() {
        Heap<Integer> numbers = new HeapUsingDynamicArray<>();
        numbers.insert(1);
        numbers.insert(2);
        numbers.insert(3);
        numbers.insert(4);
        numbers.insert(5);
        numbers.insert(6);

        assertEquals(6, numbers.size());
        assertEquals(6, numbers.delete());

        assertEquals(5, numbers.size());
        assertEquals(5, numbers.delete());

        assertEquals(4, numbers.size());
        assertEquals(4, numbers.delete());

        assertEquals(3, numbers.size());
        assertEquals(3, numbers.delete());

        assertEquals(2, numbers.size());
        assertEquals(2, numbers.delete());

        assertEquals(1, numbers.size());
        assertEquals(1, numbers.delete());

        assertEquals(0, numbers.size());
    }

    private static void insertFiveElements() {
        Heap<Integer> numbers = new HeapUsingDynamicArray<>();
        numbers.insert(1);
        numbers.insert(2);
        numbers.insert(3);
        numbers.insert(4);
        numbers.insert(5);

        assertEquals(5, numbers.size());
        assertEquals(5, numbers.delete());

        assertEquals(4, numbers.size());
        assertEquals(4, numbers.delete());

        assertEquals(3, numbers.size());
        assertEquals(3, numbers.delete());

        assertEquals(2, numbers.size());
        assertEquals(2, numbers.delete());

        assertEquals(1, numbers.size());
        assertEquals(1, numbers.delete());

        assertEquals(0, numbers.size());
    }

    private static void insertFourElements() {
        Heap<Integer> numbers = new HeapUsingDynamicArray<>();
        numbers.insert(1);
        numbers.insert(2);
        numbers.insert(3);
        numbers.insert(4);

        assertEquals(4, numbers.size());
        assertEquals(4, numbers.delete());

        assertEquals(3, numbers.size());
        assertEquals(3, numbers.delete());

        assertEquals(2, numbers.size());
        assertEquals(2, numbers.delete());

        assertEquals(1, numbers.size());
        assertEquals(1, numbers.delete());

        assertEquals(0, numbers.size());
    }

    private static void insertThreeElements() {
        Heap<Integer> numbers = new HeapUsingDynamicArray<>();
        numbers.insert(1);
        numbers.insert(2);
        numbers.insert(3);

        assertEquals(3, numbers.size());
        assertEquals(3, numbers.delete());

        assertEquals(2, numbers.size());
        assertEquals(2, numbers.delete());

        assertEquals(1, numbers.size());
        assertEquals(1, numbers.delete());

        assertEquals(0, numbers.size());
    }

    private static void insertTwoElements() {
        Heap<Integer> numbers = new HeapUsingDynamicArray<>();
        numbers.insert(1);
        numbers.insert(2);

        assertEquals(2, numbers.size());
        assertEquals(2, numbers.delete());

        assertEquals(1, numbers.size());
        assertEquals(1, numbers.delete());

        assertEquals(0, numbers.size());
    }

    private static void insertOneElement() {
        Heap<Integer> numbers = new HeapUsingDynamicArray<>();
        numbers.insert(1);

        assertEquals(1, numbers.size());
        assertEquals(1, numbers.delete());

        assertEquals(0, numbers.size());
    }

    private static void withSixElements() {
        ArrayList<Integer> unOrdered = new ArrayList<>();
        unOrdered.add(1);
        unOrdered.add(2);
        unOrdered.add(3);
        unOrdered.add(4);
        unOrdered.add(5);
        unOrdered.add(6);
        Heap<Integer> numbers = new HeapUsingDynamicArray<>(unOrdered);

        assertEquals(6, numbers.size());
        assertEquals(6, numbers.delete());

        assertEquals(5, numbers.size());
        assertEquals(5, numbers.delete());

        assertEquals(4, numbers.size());
        assertEquals(4, numbers.delete());

        assertEquals(3, numbers.size());
        assertEquals(3, numbers.delete());

        assertEquals(2, numbers.size());
        assertEquals(2, numbers.delete());

        assertEquals(1, numbers.size());
        assertEquals(1, numbers.delete());

        assertEquals(0, numbers.size());
    }

    private static void withSixElementsAndComparator() {
        ArrayList<Integer> unOrdered = new ArrayList<>();
        unOrdered.add(6);
        unOrdered.add(5);
        unOrdered.add(4);
        unOrdered.add(3);
        unOrdered.add(2);
        unOrdered.add(1);
        Heap<Integer> numbers = new HeapUsingDynamicArray<>(unOrdered, Comparator.reverseOrder());

        assertEquals(6, numbers.size());
        assertEquals(1, numbers.delete());

        assertEquals(5, numbers.size());
        assertEquals(2, numbers.delete());

        assertEquals(4, numbers.size());
        assertEquals(3, numbers.delete());

        assertEquals(3, numbers.size());
        assertEquals(4, numbers.delete());

        assertEquals(2, numbers.size());
        assertEquals(5, numbers.delete());

        assertEquals(1, numbers.size());
        assertEquals(6, numbers.delete());

        assertEquals(0, numbers.size());
    }

    private static void withFiveElements() {
        ArrayList<Integer> unOrdered = new ArrayList<>();
        unOrdered.add(1);
        unOrdered.add(2);
        unOrdered.add(3);
        unOrdered.add(4);
        unOrdered.add(5);
        Heap<Integer> numbers = new HeapUsingDynamicArray<>(unOrdered);

        assertEquals(5, numbers.size());
        assertEquals(5, numbers.delete());

        assertEquals(4, numbers.size());
        assertEquals(4, numbers.delete());

        assertEquals(3, numbers.size());
        assertEquals(3, numbers.delete());

        assertEquals(2, numbers.size());
        assertEquals(2, numbers.delete());

        assertEquals(1, numbers.size());
        assertEquals(1, numbers.delete());

        assertEquals(0, numbers.size());
    }

    private static void withFiveElementsAndComparator() {
        ArrayList<Integer> unOrdered = new ArrayList<>();
        unOrdered.add(5);
        unOrdered.add(4);
        unOrdered.add(3);
        unOrdered.add(2);
        unOrdered.add(1);
        Heap<Integer> numbers = new HeapUsingDynamicArray<>(unOrdered, Comparator.reverseOrder());

        assertEquals(5, numbers.size());
        assertEquals(1, numbers.delete());

        assertEquals(4, numbers.size());
        assertEquals(2, numbers.delete());

        assertEquals(3, numbers.size());
        assertEquals(3, numbers.delete());

        assertEquals(2, numbers.size());
        assertEquals(4, numbers.delete());

        assertEquals(1, numbers.size());
        assertEquals(5, numbers.delete());

        assertEquals(0, numbers.size());
    }

    private static void withFourElements() {
        ArrayList<Integer> unOrdered = new ArrayList<>();
        unOrdered.add(1);
        unOrdered.add(2);
        unOrdered.add(3);
        unOrdered.add(4);
        Heap<Integer> numbers = new HeapUsingDynamicArray<>(unOrdered);

        assertEquals(4, numbers.size());
        assertEquals(4, numbers.delete());

        assertEquals(3, numbers.size());
        assertEquals(3, numbers.delete());

        assertEquals(2, numbers.size());
        assertEquals(2, numbers.delete());

        assertEquals(1, numbers.size());
        assertEquals(1, numbers.delete());

        assertEquals(0, numbers.size());
    }

    private static void withFourElementsAndComparator() {
        ArrayList<Integer> unOrdered = new ArrayList<>();
        unOrdered.add(4);
        unOrdered.add(3);
        unOrdered.add(2);
        unOrdered.add(1);
        Heap<Integer> numbers = new HeapUsingDynamicArray<>(unOrdered, Comparator.reverseOrder());

        assertEquals(4, numbers.size());
        assertEquals(1, numbers.delete());

        assertEquals(3, numbers.size());
        assertEquals(2, numbers.delete());

        assertEquals(2, numbers.size());
        assertEquals(3, numbers.delete());

        assertEquals(1, numbers.size());
        assertEquals(4, numbers.delete());

        assertEquals(0, numbers.size());
    }

    private static void withThreeElements() {
        ArrayList<Integer> unOrdered = new ArrayList<>();
        unOrdered.add(1);
        unOrdered.add(2);
        unOrdered.add(3);
        Heap<Integer> numbers = new HeapUsingDynamicArray<>(unOrdered);

        assertEquals(3, numbers.size());
        assertEquals(3, numbers.delete());

        assertEquals(2, numbers.size());
        assertEquals(2, numbers.delete());

        assertEquals(1, numbers.size());
        assertEquals(1, numbers.delete());

        assertEquals(0, numbers.size());
    }

    private static void withThreeElementsAndComparator() {
        ArrayList<Integer> unOrdered = new ArrayList<>();
        unOrdered.add(3);
        unOrdered.add(2);
        unOrdered.add(1);
        Heap<Integer> numbers = new HeapUsingDynamicArray<>(unOrdered, Comparator.reverseOrder());

        assertEquals(3, numbers.size());
        assertEquals(1, numbers.delete());

        assertEquals(2, numbers.size());
        assertEquals(2, numbers.delete());

        assertEquals(1, numbers.size());
        assertEquals(3, numbers.delete());

        assertEquals(0, numbers.size());
    }

    private static void withTwoElements() {
        ArrayList<Integer> unOrdered = new ArrayList<>();
        unOrdered.add(1);
        unOrdered.add(2);
        Heap<Integer> numbers = new HeapUsingDynamicArray<>(unOrdered);

        assertEquals(2, numbers.size());
        assertEquals(2, numbers.delete());

        assertEquals(1, numbers.size());
        assertEquals(1, numbers.delete());

        assertEquals(0, numbers.size());
    }

    private static void withTwoElementsAndComparator() {
        ArrayList<Integer> unOrdered = new ArrayList<>();
        unOrdered.add(2);
        unOrdered.add(1);
        Heap<Integer> numbers = new HeapUsingDynamicArray<>(unOrdered, Comparator.reverseOrder());

        assertEquals(2, numbers.size());
        assertEquals(1, numbers.delete());

        assertEquals(1, numbers.size());
        assertEquals(2, numbers.delete());

        assertEquals(0, numbers.size());
    }

    private static void withOneElement() {
        ArrayList<Integer> unOrdered = new ArrayList<>();
        unOrdered.add(1);

        Heap<Integer> numbers = new HeapUsingDynamicArray<>(unOrdered);
        assertEquals(1, numbers.size());
        assertEquals(1, numbers.delete());

        assertEquals(0, numbers.size());
    }

    private static void withOneElementAndComparator() {
        ArrayList<Integer> unOrdered = new ArrayList<>();
        unOrdered.add(1);

        Heap<Integer> numbers = new HeapUsingDynamicArray<>(unOrdered, Comparator.reverseOrder());
        assertEquals(1, numbers.size());
        assertEquals(1, numbers.delete());

        assertEquals(0, numbers.size());
    }
}