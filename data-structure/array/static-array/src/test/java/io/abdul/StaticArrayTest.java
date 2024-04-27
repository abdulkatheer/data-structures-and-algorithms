package io.abdul;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StaticArrayTest {
    @Test
    void addAtPosition() {
        StaticArray<Integer> numbers = new StaticArray<>(10);

        numbers.set(0, 1);
        assertEquals(1, numbers.get(0));
        assertEquals(1, numbers.set(0, 2));
        assertEquals(2, numbers.get(0));
        numbers.set(9, 10);
        assertEquals(10, numbers.get(9));
        assertThrows(IllegalArgumentException.class, () -> numbers.set(10, 100));
    }

    @Test
    void insertAtPosition() {
        StaticArray<Integer> numbers = new StaticArray<>(10);

        numbers.insert(0, 1);
        assertEquals(1, numbers.get(0));
        numbers.insert(0, 2);
        assertEquals(2, numbers.get(0));
        assertEquals(1, numbers.get(1));
        numbers.insert(0, 10);
        assertEquals(10, numbers.get(0));
        assertEquals(2, numbers.get(1));
        assertEquals(1, numbers.get(2));
        numbers.insert(0, 11);
        numbers.insert(0, 12);
        numbers.insert(0, 13);
        numbers.insert(0, 14);
        numbers.insert(0, 15);
        numbers.insert(0, 16);
        numbers.insert(0, 17);
        assertEquals(17, numbers.get(0));
        assertEquals(16, numbers.get(1));
        assertEquals(15, numbers.get(2));
        assertEquals(14, numbers.get(3));
        assertEquals(13, numbers.get(4));
        assertEquals(12, numbers.get(5));
        assertEquals(11, numbers.get(6));
        numbers.insert(0, 18);
        assertEquals(18, numbers.get(0));
        assertEquals(2, numbers.get(9));
    }

    @Test
    void findAtIndex() {
        StaticArray<Integer> numbers = new StaticArray<>(10);

        numbers.insert(0, 1);
        assertEquals(1, numbers.get(0));
        assertNull(numbers.get(1));
    }

    @Test
    void search() {
        StaticArray<Integer> numbers = new StaticArray<>(10);

        assertEquals(-1, numbers.lookup(10));
        numbers.insert(0, 10);
        assertEquals(0, numbers.lookup(10));
        numbers.insert(0, 10);
        numbers.insert(0, 10);
        numbers.insert(0, 10);
        assertEquals(0, numbers.lookup(10));
    }

    @Test
    void removeAtPosition() {
        StaticArray<Integer> numbers = new StaticArray<>(10);

        numbers.insert(0, 10);
        numbers.insert(0, 11);
        numbers.insert(0, 12);
        numbers.insert(0, 13);
        assertEquals(13, numbers.remove(0));
        assertEquals(12, numbers.remove(0));
        assertEquals(11, numbers.remove(0));
        assertEquals(10, numbers.remove(0));
    }
}