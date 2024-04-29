package io.abdul;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class DynamicArrayTest {

    @Test
    void add() throws NoSuchFieldException, IllegalAccessException {
        DynamicArray<Integer> numbers = new DynamicArray<>(1);

        Object[] elements;

        assertEquals(0, numbers.add(1));
        elements = getElements(numbers);
        assertEquals(1, elements[0]);
        assertEquals(1, numbers.size());

        assertEquals(1, numbers.add(2));
        elements = getElements(numbers);
        assertEquals(2, elements[1]);
        assertEquals(2, numbers.size());

        assertEquals(2, numbers.add(3));
        elements = getElements(numbers);
        assertEquals(3, elements[2]);
        assertEquals(3, numbers.size());

        assertEquals(3, numbers.add(4));
        elements = getElements(numbers);
        assertEquals(4, elements[3]);
        assertEquals(4, numbers.size());

        assertEquals(4, numbers.add(5));
        elements = getElements(numbers);
        assertEquals(5, elements[4]);
        assertEquals(5, numbers.size());
    }

    @Test
    void set() throws NoSuchFieldException, IllegalAccessException {
        DynamicArray<Integer> numbers = new DynamicArray<>(1);

        Object[] elements;

        assertThrows(IllegalArgumentException.class, () -> numbers.set(0, 1));
        numbers.add(1);
        elements = getElements(numbers);
        assertEquals(1, elements[0]);
        assertEquals(1, numbers.set(0, 10));
        assertEquals(10, elements[0]);
    }

    @Test
    void insert() throws NoSuchFieldException, IllegalAccessException {
        DynamicArray<Integer> numbers = new DynamicArray<>(1);

        Object[] elements;

        numbers.add(10);
        elements = getElements(numbers);
        assertEquals(10, elements[0]);
        assertEquals(1, numbers.size());

        numbers.insert(0, 9);
        elements = getElements(numbers);
        assertEquals(9, elements[0]);
        assertEquals(2, numbers.size());

        numbers.insert(0, 8);
        elements = getElements(numbers);
        assertEquals(8, elements[0]);
        assertEquals(3, numbers.size());

        numbers.insert(0, 7);
        elements = getElements(numbers);
        assertEquals(7, elements[0]);
        assertEquals(4, numbers.size());

        numbers.insert(0, 6);
        elements = getElements(numbers);
        assertEquals(6, elements[0]);
        assertEquals(5, numbers.size());

        numbers.insert(0, 5);
        elements = getElements(numbers);
        assertEquals(5, elements[0]);
        assertEquals(6, numbers.size());

        numbers.insert(0, 4);
        elements = getElements(numbers);
        assertEquals(4, elements[0]);
        assertEquals(7, numbers.size());

        numbers.insert(0, 3);
        elements = getElements(numbers);
        assertEquals(3, elements[0]);
        assertEquals(8, numbers.size());

        numbers.insert(0, 2);
        elements = getElements(numbers);
        assertEquals(2, elements[0]);
        assertEquals(9, numbers.size());

        numbers.insert(0, 1);
        elements = getElements(numbers);
        assertEquals(1, elements[0]);
        assertEquals(10, numbers.size());
    }

    @Test
    void remove() throws NoSuchFieldException, IllegalAccessException {
        DynamicArray<Integer> numbers = new DynamicArray<>(1);

        Object[] elements;

        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        numbers.add(6);
        numbers.add(7);
        numbers.add(8);
        numbers.add(9);
        numbers.add(10);

        assertEquals(1, numbers.remove(0));
        elements = getElements(numbers);
        assertEquals(2, elements[0]);
        assertEquals(9, numbers.size());

        assertEquals(2, numbers.remove(0));
        elements = getElements(numbers);
        assertEquals(3, elements[0]);
        assertEquals(8, numbers.size());

        assertEquals(3, numbers.remove(0));
        elements = getElements(numbers);
        assertEquals(4, elements[0]);
        assertEquals(7, numbers.size());

        assertEquals(4, numbers.remove(0));
        elements = getElements(numbers);
        assertEquals(5, elements[0]);
        assertEquals(6, numbers.size());

        assertEquals(5, numbers.remove(0));
        elements = getElements(numbers);
        assertEquals(6, elements[0]);
        assertEquals(5, numbers.size());

        assertEquals(6, numbers.remove(0));
        elements = getElements(numbers);
        assertEquals(7, elements[0]);
        assertEquals(4, numbers.size());

        assertEquals(7, numbers.remove(0));
        elements = getElements(numbers);
        assertEquals(8, elements[0]);
        assertEquals(3, numbers.size());

        assertEquals(8, numbers.remove(0));
        elements = getElements(numbers);
        assertEquals(9, elements[0]);
        assertEquals(2, numbers.size());

        assertEquals(9, numbers.remove(0));
        elements = getElements(numbers);
        assertEquals(10, elements[0]);
        assertEquals(1, numbers.size());

        assertEquals(10, numbers.remove(0));
        elements = getElements(numbers);
        assertNull(elements[0]);
        assertEquals(0, numbers.size());
    }

    @Test
    void find() {
        DynamicArray<Integer> numbers = new DynamicArray<>(10);

        numbers.add(1);
        assertEquals(1, numbers.get(0));
        assertThrows(IllegalArgumentException.class, () -> numbers.get(1));
    }

    private static Object[] getElements(DynamicArray<Integer> numbers) throws NoSuchFieldException, IllegalAccessException {
        Field elementsField = numbers.getClass().getDeclaredField("elements");
        elementsField.setAccessible(true);
        return (Object[]) elementsField.get(numbers);
    }

    private int getSize(DynamicArray<Integer> numbers) throws NoSuchFieldException, IllegalAccessException {
        Field elementsField = numbers.getClass().getDeclaredField("size");
        elementsField.setAccessible(true);
        return elementsField.getInt(numbers);
    }
}