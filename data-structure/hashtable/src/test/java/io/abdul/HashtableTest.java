package io.abdul;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HashtableTest {

    @Test
    void put() throws NoSuchFieldException, IllegalAccessException {
        Hashtable<String, Integer> priceList = new Hashtable<>(5);

        Hashtable.Element<String, Integer>[] table;

        table = getTable(priceList);
        assertEquals(5, table.length);

        priceList.put("Grapes", 100);
        table = getTable(priceList);
        assertEquals("Grapes", table[0].getKey());
        assertEquals(100, table[0].getValue());

        priceList.put("Apple", 120);
        table = getTable(priceList);
        assertEquals("Apple", table[3].getKey());
        assertEquals(120, table[3].getValue());

        Integer oldValue = priceList.put("Grapes", 300);
        table = getTable(priceList);
        assertEquals("Grapes", table[0].getKey());
        assertEquals(300, table[0].getValue());
        assertEquals(100, oldValue);

        priceList.put("Mango", 130);
        table = getTable(priceList);
        assertEquals("Mango", table[3].getKey());
        assertEquals(130, table[3].getValue());
        assertEquals("Apple", table[3].getNext().getKey());
        assertEquals(120, table[3].getNext().getValue());

        priceList.put("Orange", 60);
        assertEquals("Orange", table[1].getKey());
        assertEquals(60, table[1].getValue());

        priceList.put("Pine Apple", 89);
        assertEquals("Pine Apple", table[1].getKey());
        assertEquals(89, table[1].getValue());
        assertEquals("Orange", table[1].getNext().getKey());
        assertEquals(60, table[1].getNext().getValue());
    }

    @Test
    void get() {
        Hashtable<String, Integer> priceList = new Hashtable<>(5);

        priceList.put("Grapes", 100);
        priceList.put("Apple", 120);
        assertEquals(100, priceList.get("Grapes"));
        assertEquals(120, priceList.get("Apple"));
        priceList.put("Grapes", 300);
        assertEquals(300, priceList.get("Grapes"));
        priceList.put("Mango", 130);
        priceList.put("Orange", 60);
        priceList.put("Pine Apple", 89);
        assertEquals(130, priceList.get("Mango"));
        assertEquals(60, priceList.get("Orange"));
        assertEquals(89, priceList.get("Pine Apple"));
    }

    @Test
    void keys() {
        Hashtable<String, Integer> priceList = new Hashtable<>(5);

        priceList.put("Grapes", 100);
        priceList.put("Apple", 120);
        assertEquals(2, priceList.keys().size());
        priceList.put("Grapes", 300);
        assertEquals(2, priceList.keys().size());
        priceList.put("Mango", 130);
        priceList.put("Orange", 60);
        priceList.put("Pine Apple", 89);
        assertEquals(5, priceList.keys().size());
    }

    private static <K, V> Hashtable.Element<K, V>[] getTable(Hashtable<K, V> numbers) throws NoSuchFieldException, IllegalAccessException {
        Field elementsField = numbers.getClass().getDeclaredField("table");
        elementsField.setAccessible(true);
        return (Hashtable.Element<K, V>[]) elementsField.get(numbers);
    }
}