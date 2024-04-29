package io.abdul;

import io.abdul.api.exception.IndexOutOfBounds;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class StaticArrayTest {
    @Test
    void add() throws NoSuchFieldException, IllegalAccessException {
        StaticArray<Integer> numbers = new StaticArray<>(5);

        Object[] elements;

        assertEquals(0, numbers.size());

        numbers.add(1);
        assertEquals(1, numbers.size());
        elements = getElements(numbers);
        assertEquals(1, elements[0]);

        numbers.add(2);
        assertEquals(2, numbers.size());
        elements = getElements(numbers);
        assertEquals(1, elements[0]);
        assertEquals(2, elements[1]);

        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        assertEquals(5, numbers.size());

        assertThrows(IndexOutOfBounds.class, () -> numbers.add(6));
    }

    @Test
    void set() throws NoSuchFieldException, IllegalAccessException {
        StaticArray<Integer> numbers = new StaticArray<>(6);

        Object[] elements;

        numbers.add(1);
        elements = getElements(numbers);
        assertEquals(1, elements[0]);
        assertEquals(1, numbers.set(0, 2));
        assertEquals(2, elements[0]);

        numbers.add(3);
        assertEquals(3, elements[1]);
        assertEquals(3, numbers.set(1, 4));
        assertEquals(2, elements[0]);
        assertEquals(4, elements[1]);

        numbers.add(5);
        numbers.add(6);
        numbers.add(7);
        assertEquals(5, numbers.set(2, 6));
        assertEquals(6, numbers.set(3, 8));
        assertEquals(7, numbers.set(4, 8));

        assertThrows(IndexOutOfBounds.class, () -> numbers.set(6, 100));
    }

    @Test
    void insert() throws NoSuchFieldException, IllegalAccessException {
        StaticArray<Integer> numbers = new StaticArray<>(5);

        Object[] elements;

        elements = getElements(numbers);

        assertThrows(IndexOutOfBounds.class, () -> numbers.insert(0, 1));
        numbers.add(5);
        assertEquals(1, numbers.size());
        numbers.insert(0, 2);
        assertEquals(2, numbers.size());
        assertEquals(2, elements[0]);
        assertEquals(5, elements[1]);

        numbers.insert(0, 1);
        assertEquals(3, numbers.size());
        assertEquals(1, elements[0]);
        assertEquals(2, elements[1]);
        assertEquals(5, elements[2]);

        numbers.insert(2, 3);
        numbers.insert(3, 4);
        assertEquals(5, numbers.size());
        assertEquals(1, elements[0]);
        assertEquals(2, elements[1]);
        assertEquals(3, elements[2]);
        assertEquals(4, elements[3]);
        assertEquals(5, elements[4]);
    }

    @Test
    void get() {
        StaticArray<Integer> numbers = new StaticArray<>(10);

        assertThrows(IndexOutOfBounds.class, () -> numbers.get(0));
        numbers.add(1);
        assertEquals(1, numbers.get(0));
    }

    @Test
    void lookup() {
        StaticArray<Integer> numbers = new StaticArray<>(10);

        assertTrue(numbers.lookup(10).isEmpty());
        numbers.add(10);
        assertEquals(0, numbers.lookup(10).get());
        numbers.insert(0, 10);
        numbers.insert(0, 10);
        numbers.insert(0, 10);
        assertEquals(0, numbers.lookup(10).get());
    }

    @Test
    void remove() {
        StaticArray<Integer> numbers = new StaticArray<>(10);

        numbers.add(10);
        numbers.insert(0, 11);
        numbers.insert(0, 12);
        numbers.insert(0, 13);
        assertEquals(13, numbers.remove(0));
        assertEquals(12, numbers.remove(0));
        assertEquals(11, numbers.remove(0));
        assertEquals(10, numbers.remove(0));
    }

    private static Object[] getElements(StaticArray numbers) throws NoSuchFieldException, IllegalAccessException {
        Field elementsField = numbers.getClass().getDeclaredField("elements");
        elementsField.setAccessible(true);
        return (Object[]) elementsField.get(numbers);
    }
}