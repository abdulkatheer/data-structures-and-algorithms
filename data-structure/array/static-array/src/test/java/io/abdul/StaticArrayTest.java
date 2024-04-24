package io.abdul;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StaticArrayTest {
    @Test
    void addAtPosition() {
        StaticArray<Integer> numbers = new StaticArray<>(10);

        numbers.addAtPosition(0, 1);
        assertEquals(1, numbers.findAtIndex(0));
        assertEquals(1, numbers.addAtPosition(0, 2));
        assertEquals(2, numbers.findAtIndex(0));
        numbers.addAtPosition(9, 10);
        assertEquals(10, numbers.findAtIndex(9));
        assertThrows(IllegalArgumentException.class, () -> numbers.addAtPosition(10, 100));
    }

    @Test
    void insertAtPosition() {
        StaticArray<Integer> numbers = new StaticArray<>(10);

        numbers.insertAtPosition(0, 1);
        assertEquals(1, numbers.findAtIndex(0));
        numbers.insertAtPosition(0, 2);
        assertEquals(2, numbers.findAtIndex(0));
        assertEquals(1, numbers.findAtIndex(1));
        numbers.insertAtPosition(0, 10);
        assertEquals(10, numbers.findAtIndex(0));
        assertEquals(2, numbers.findAtIndex(1));
        assertEquals(1, numbers.findAtIndex(2));
        numbers.insertAtPosition(0, 11);
        numbers.insertAtPosition(0, 12);
        numbers.insertAtPosition(0, 13);
        numbers.insertAtPosition(0, 14);
        numbers.insertAtPosition(0, 15);
        numbers.insertAtPosition(0, 16);
        numbers.insertAtPosition(0, 17);
        assertEquals(17, numbers.findAtIndex(0));
        assertEquals(16, numbers.findAtIndex(1));
        assertEquals(15, numbers.findAtIndex(2));
        assertEquals(14, numbers.findAtIndex(3));
        assertEquals(13, numbers.findAtIndex(4));
        assertEquals(12, numbers.findAtIndex(5));
        assertEquals(11, numbers.findAtIndex(6));
        numbers.insertAtPosition(0, 18);
        assertEquals(18, numbers.findAtIndex(0));
        assertEquals(2, numbers.findAtIndex(9));
    }

    @Test
    void findAtIndex() {
        StaticArray<Integer> numbers = new StaticArray<>(10);

        numbers.insertAtPosition(0, 1);
        assertEquals(1, numbers.findAtIndex(0));
        assertNull(numbers.findAtIndex(1));
    }

    @Test
    void search() {
        StaticArray<Integer> numbers = new StaticArray<>(10);

        assertEquals(-1, numbers.search(10));
        numbers.insertAtPosition(0, 10);
        assertEquals(0, numbers.search(10));
        numbers.insertAtPosition(0, 10);
        numbers.insertAtPosition(0, 10);
        numbers.insertAtPosition(0, 10);
        assertEquals(0, numbers.search(10));
    }

    @Test
    void removeAtPosition() {
        StaticArray<Integer> numbers = new StaticArray<>(10);

        numbers.insertAtPosition(0, 10);
        numbers.insertAtPosition(0, 11);
        numbers.insertAtPosition(0, 12);
        numbers.insertAtPosition(0, 13);
        assertEquals(13, numbers.removeAtPosition(0));
        assertEquals(12, numbers.removeAtPosition(0));
        assertEquals(11, numbers.removeAtPosition(0));
        assertEquals(10, numbers.removeAtPosition(0));
    }
}