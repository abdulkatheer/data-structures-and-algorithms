package io.abdul;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class SinglyLinkedListTest {

    @Test
    void search() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();

        list.insertAtTheBeginning(10);
        list.insertInTheMiddle(0, 9);
        list.insertAtTheEnd(12);
        list.insertInTheMiddle(1, 14);
        list.insertAtTheBeginning(1);
        list.insertAtTheEnd(20);
        list.insertInTheMiddle(1, 2);
        list.insertInTheMiddle(2, 3);
        list.insertInTheMiddle(3, 4);

        /*
        1
        2
        3
        4
        9
        14
        10
        12
        20
         */
        assertEquals(0, list.search(1));
        assertEquals(1, list.search(2));
        assertEquals(2, list.search(3));
        assertEquals(3, list.search(4));
        assertEquals(4, list.search(9));
        assertEquals(5, list.search(14));
        assertEquals(6, list.search(10));
        assertEquals(7, list.search(12));
        assertEquals(8, list.search(20));
    }

    @Test
    void insertAtTheBeginning() throws NoSuchFieldException, IllegalAccessException {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();

        SinglyLinkedList.Node<Integer> tail;
        SinglyLinkedList.Node<Integer> head;

        list.insertAtTheBeginning(10);
        assertEquals(1, list.size());
        head = getHead(list);
        assertEquals(10, head.getValue());
        assertNull(head.getNext());
        tail = getTail(list);
        assertEquals(head, tail);

        list.insertAtTheBeginning(9);
        assertEquals(2, list.size());
        head = getHead(list);
        assertEquals(9, head.getValue());
        assertNotNull(head.getNext());
        assertEquals(10, head.getNext().getValue());
        assertNull(head.getNext().getNext());

        list.insertAtTheBeginning(8);
        assertEquals(3, list.size());
        head = getHead(list);
        assertEquals(8, head.getValue());
        assertNotNull(head.getNext());
        assertEquals(9, head.getNext().getValue());
        assertNotNull(head.getNext().getNext());
        assertEquals(10, head.getNext().getNext().getValue());
        assertNull(head.getNext().getNext().getNext());
    }

    @Test
    void insertAtTheEnd() throws NoSuchFieldException, IllegalAccessException {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();

        SinglyLinkedList.Node<Integer> tail;
        SinglyLinkedList.Node<Integer> head;

        list.insertAtTheEnd(10);
        assertEquals(1, list.size());
        head = getHead(list);
        tail = getTail(list);
        assertEquals(head, tail);
        assertEquals(10, head.getValue());
        assertNull(head.getNext());

        list.insertAtTheEnd(9);
        assertEquals(2, list.size());
        head = getHead(list);
        tail = getTail(list);
        assertEquals(10, head.getValue());
        assertNotNull(head.getNext());
        assertEquals(9, head.getNext().getValue());
        assertNull(head.getNext().getNext());
        assertEquals(tail, head.getNext());

        list.insertAtTheEnd(8);
        assertEquals(3, list.size());
        head = getHead(list);
        tail = getTail(list);
        assertEquals(10, head.getValue());
        assertNotNull(head.getNext());
        assertEquals(9, head.getNext().getValue());
        assertNotNull(head.getNext().getNext());
        assertEquals(8, head.getNext().getNext().getValue());
        assertNull(head.getNext().getNext().getNext());
        assertEquals(tail, head.getNext().getNext());

        list.insertAtTheEnd(7);
        assertEquals(4, list.size());
        head = getHead(list);
        tail = getTail(list);
        assertEquals(10, head.getValue());
        assertNotNull(head.getNext());
        assertEquals(9, head.getNext().getValue());
        assertNotNull(head.getNext().getNext());
        assertEquals(8, head.getNext().getNext().getValue());
        assertNotNull(head.getNext().getNext().getNext());
        assertEquals(7, head.getNext().getNext().getNext().getValue());
        assertNull(head.getNext().getNext().getNext().getNext());
        assertEquals(tail, head.getNext().getNext().getNext());
    }

    @Test
    void insertInTheMiddle() throws NoSuchFieldException, IllegalAccessException {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();

        SinglyLinkedList.Node<Integer> tail;
        SinglyLinkedList.Node<Integer> head;

        assertThrows(IllegalArgumentException.class, () -> list.insertInTheMiddle(0, 1));

        list.insertAtTheBeginning(10);

        list.insertInTheMiddle(0, 9);
        assertEquals(2, list.size());
        head = getHead(list);
        tail = getTail(list);
        assertEquals(9, head.getValue());
        assertNotNull(head.getNext());
        assertEquals(10, head.getNext().getValue());
        assertNull(head.getNext().getNext());
        assertEquals(tail, head.getNext());

        list.insertInTheMiddle(0, 8);
        assertEquals(3, list.size());
        head = getHead(list);
        tail = getTail(list);
        assertEquals(8, head.getValue());
        assertNotNull(head.getNext());
        assertEquals(9, head.getNext().getValue());
        assertNotNull(head.getNext().getNext());
        assertEquals(10, head.getNext().getNext().getValue());
        assertNull(head.getNext().getNext().getNext());
        assertEquals(tail, head.getNext().getNext());

        list.insertInTheMiddle(0, 7);
        assertEquals(4, list.size());
        head = getHead(list);
        tail = getTail(list);
        assertEquals(7, head.getValue());
        assertNotNull(head.getNext());
        assertEquals(8, head.getNext().getValue());
        assertNotNull(head.getNext().getNext());
        assertEquals(9, head.getNext().getNext().getValue());
        assertNotNull(head.getNext().getNext().getNext());
        assertEquals(10, head.getNext().getNext().getNext().getValue());
        assertNull(head.getNext().getNext().getNext().getNext());
        assertEquals(tail, head.getNext().getNext().getNext());

        list.insertInTheMiddle(1, 6);
        assertEquals(5, list.size());
        head = getHead(list);
        tail = getTail(list);
        assertEquals(7, head.getValue());
        assertNotNull(head.getNext());
        assertEquals(6, head.getNext().getValue());
        assertNotNull(head.getNext().getNext());
        assertEquals(8, head.getNext().getNext().getValue());
        assertNotNull(head.getNext().getNext().getNext());
        assertEquals(9, head.getNext().getNext().getNext().getValue());
        assertNotNull(head.getNext().getNext().getNext().getNext());
        assertEquals(10, head.getNext().getNext().getNext().getNext().getValue());
        assertNull(head.getNext().getNext().getNext().getNext().getNext());
        assertEquals(tail, head.getNext().getNext().getNext().getNext());

        list.insertInTheMiddle(4, 11);
        assertEquals(6, list.size());
        head = getHead(list);
        tail = getTail(list);
        assertEquals(7, head.getValue());
        assertNotNull(head.getNext());
        assertEquals(6, head.getNext().getValue());
        assertNotNull(head.getNext().getNext());
        assertEquals(8, head.getNext().getNext().getValue());
        assertNotNull(head.getNext().getNext().getNext());
        assertEquals(9, head.getNext().getNext().getNext().getValue());
        assertNotNull(head.getNext().getNext().getNext().getNext());
        assertEquals(10, head.getNext().getNext().getNext().getNext().getValue());
        assertNotNull(head.getNext().getNext().getNext().getNext().getNext());
        assertEquals(11, head.getNext().getNext().getNext().getNext().getNext().getValue());
        assertNull(head.getNext().getNext().getNext().getNext().getNext().getNext());
        assertEquals(tail, head.getNext().getNext().getNext().getNext().getNext());

        list.insertInTheMiddle(1, 2);
        assertEquals(7, list.size());
        head = getHead(list);
        tail = getTail(list);
        assertEquals(7, head.getValue());
        assertNotNull(head.getNext());
        assertEquals(2, head.getNext().getValue());
        assertNotNull(head.getNext().getNext());
        assertEquals(6, head.getNext().getNext().getValue());
        assertNotNull(head.getNext().getNext().getNext());
        assertEquals(8, head.getNext().getNext().getNext().getValue());
        assertNotNull(head.getNext().getNext().getNext().getNext());
        assertEquals(9, head.getNext().getNext().getNext().getNext().getValue());
        assertNotNull(head.getNext().getNext().getNext().getNext().getNext());
        assertEquals(10, head.getNext().getNext().getNext().getNext().getNext().getValue());
        assertNotNull(head.getNext().getNext().getNext().getNext().getNext().getNext());
        assertEquals(11, head.getNext().getNext().getNext().getNext().getNext().getNext().getValue());
        assertNull(head.getNext().getNext().getNext().getNext().getNext().getNext().getNext());
        assertEquals(tail, head.getNext().getNext().getNext().getNext().getNext().getNext());
    }

    @Test
    void removeHead() throws NoSuchFieldException, IllegalAccessException {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();

        list.insertAtTheBeginning(10);
        list.insertInTheMiddle(0, 9);
        list.insertAtTheEnd(12);
        list.insertInTheMiddle(1, 14);
        list.insertAtTheBeginning(1);
        list.insertAtTheEnd(20);
        list.insertInTheMiddle(1, 2);
        list.insertInTheMiddle(2, 3);
        list.insertInTheMiddle(3, 4);

        SinglyLinkedList.Node<Integer> tail;
        SinglyLinkedList.Node<Integer> head;

        head = getHead(list);
        tail = getTail(list);

        /*
        1
        2
        3
        4
        9
        14
        10
        12
        20
         */
        assertEquals(9, list.size());
        assertEquals(1, head.getValue());
        assertEquals(20, tail.getValue());

        assertEquals(1, list.removeHead());
        assertEquals(8, list.size());

        assertEquals(2, list.removeHead());
        assertEquals(7, list.size());

        assertEquals(3, list.removeHead());
        assertEquals(6, list.size());

        assertEquals(4, list.removeHead());
        assertEquals(5, list.size());

        assertEquals(9, list.removeHead());
        assertEquals(4, list.size());

        assertEquals(14, list.removeHead());
        assertEquals(3, list.size());

        assertEquals(10, list.removeHead());
        assertEquals(2, list.size());

        assertEquals(12, list.removeHead());
        assertEquals(1, list.size());

        assertEquals(20, list.removeHead());
        assertEquals(0, list.size());

        assertThrows(IllegalArgumentException.class, list::removeHead);
    }

    @Test
    void removeTail() throws NoSuchFieldException, IllegalAccessException {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();

        list.insertAtTheBeginning(10);
        list.insertInTheMiddle(0, 9);
        list.insertAtTheEnd(12);
        list.insertInTheMiddle(1, 14);
        list.insertAtTheBeginning(1);
        list.insertAtTheEnd(20);
        list.insertInTheMiddle(1, 2);
        list.insertInTheMiddle(2, 3);
        list.insertInTheMiddle(3, 4);

        SinglyLinkedList.Node<Integer> tail;
        SinglyLinkedList.Node<Integer> head;

        head = getHead(list);
        tail = getTail(list);

        /*
        1
        2
        3
        4
        9
        14
        10
        12
        20
         */
        assertEquals(9, list.size());
        assertEquals(1, head.getValue());
        assertEquals(20, tail.getValue());

        assertEquals(20, list.removeTail());
        assertEquals(8, list.size());

        assertEquals(12, list.removeTail());
        assertEquals(7, list.size());

        assertEquals(10, list.removeTail());
        assertEquals(6, list.size());

        assertEquals(14, list.removeTail());
        assertEquals(5, list.size());

        assertEquals(9, list.removeTail());
        assertEquals(4, list.size());

        assertEquals(4, list.removeTail());
        assertEquals(3, list.size());

        assertEquals(3, list.removeTail());
        assertEquals(2, list.size());

        assertEquals(2, list.removeTail());
        assertEquals(1, list.size());

        assertEquals(1, list.removeTail());
        assertEquals(0, list.size());

        assertThrows(IllegalArgumentException.class, list::removeTail);
    }

    @Test
    void removeAt() throws NoSuchFieldException, IllegalAccessException {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();

        list.insertAtTheBeginning(10);
        list.insertInTheMiddle(0, 9);
        list.insertAtTheEnd(12);
        list.insertInTheMiddle(1, 14);
        list.insertAtTheBeginning(1);
        list.insertAtTheEnd(20);
        list.insertInTheMiddle(1, 2);
        list.insertInTheMiddle(2, 3);
        list.insertInTheMiddle(3, 4);

        SinglyLinkedList.Node<Integer> tail;
        SinglyLinkedList.Node<Integer> head;

        head = getHead(list);
        tail = getTail(list);

        /*
        1
        2
        3
        4
        9
        14
        10
        12
        20
         */
        assertEquals(9, list.size());
        assertEquals(1, head.getValue());
        assertEquals(20, tail.getValue());

        assertEquals(20, list.removeAt(8)); // last element
        assertEquals(8, list.size());

        /*
        1
        2
        3
        4
        9
        14
        10
        12
         */
        assertEquals(1, list.removeAt(0)); // first element
        assertEquals(7, list.size());

        /*
        2
        3
        4
        9
        14
        10
        12
         */
        assertEquals(3, list.removeAt(1)); // second element
        assertEquals(6, list.size());

        /*
        2
        4
        9
        14
        10
        12
         */
        assertEquals(9, list.removeAt(2)); // middle element
        assertEquals(5, list.size());

        /*
        2
        4
        14
        10
        12
         */
        assertEquals(10, list.removeAt(3)); // last element
        assertEquals(4, list.size());

        /*
        2
        4
        14
        12
         */
        assertEquals(4, list.removeAt(1)); // second element
        assertEquals(3, list.size());

        /*
        2
        14
        12
         */
        assertEquals(2, list.removeAt(0));
        assertEquals(2, list.size());

        /*
        14
        12
         */
        assertEquals(12, list.removeAt(1));
        assertEquals(1, list.size());

        /*
        14
         */
        assertEquals(14, list.removeAt(0));
        assertEquals(0, list.size());

        assertThrows(IllegalArgumentException.class, () -> list.removeAt(0));
    }

    @Test
    void get() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();

        list.insertAtTheBeginning(10);
        list.insertInTheMiddle(0, 9);
        list.insertAtTheEnd(12);
        list.insertInTheMiddle(1, 14);
        list.insertAtTheBeginning(1);
        list.insertAtTheEnd(20);
        list.insertInTheMiddle(1, 2);
        list.insertInTheMiddle(2, 3);
        list.insertInTheMiddle(3, 4);

        /*
        1
        2
        3
        4
        9
        14
        10
        12
        20
         */

        assertEquals(20, list.get(8));
        assertEquals(12, list.get(7));
        assertEquals(10, list.get(6));
        assertEquals(14, list.get(5));
        assertEquals(9, list.get(4));
        assertEquals(4, list.get(3));
        assertEquals(3, list.get(2));
        assertEquals(2, list.get(1));
        assertEquals(1, list.get(0));
    }

    private static <E> SinglyLinkedList.Node<E> getHead(SinglyLinkedList<E> numbers) throws NoSuchFieldException, IllegalAccessException {
        Field elementsField = numbers.getClass().getDeclaredField("head");
        elementsField.setAccessible(true);
        return (SinglyLinkedList.Node<E>) elementsField.get(numbers);
    }

    private static <E> SinglyLinkedList.Node<E> getTail(SinglyLinkedList<E> numbers) throws NoSuchFieldException, IllegalAccessException {
        Field elementsField = numbers.getClass().getDeclaredField("tail");
        elementsField.setAccessible(true);
        return (SinglyLinkedList.Node<E>) elementsField.get(numbers);
    }
}