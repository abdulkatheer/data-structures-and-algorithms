package io.abdul;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class DoublyLinkedListTest {

    @Test
    void search() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

        list.insertAtTheBeginning(10);
        list.insert(0, 9);
        list.insertAtTheEnd(12);
        list.insert(1, 14);
        list.insertAtTheBeginning(1);
        list.insertAtTheEnd(20);
        list.insert(1, 2);
        list.insert(2, 3);
        list.insert(3, 4);

        list.printList();

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
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

        DoublyLinkedList.Node<Integer> tail;
        DoublyLinkedList.Node<Integer> head;

        list.insertAtTheBeginning(10);
        assertEquals(1, list.size());
        head = getHead(list);
        assertNull(head.getNext());
        tail = getTail(list);
        assertEquals(head, tail);
        assertEquals("10", list.toString());

        list.insertAtTheBeginning(9);
        assertEquals(2, list.size());
        head = getHead(list);
        assertNotNull(head.getNext());
        assertNull(head.getNext().getNext());
        assertEquals("9 --> 10", list.toString());

        list.insertAtTheBeginning(8);
        assertEquals(3, list.size());
        head = getHead(list);
        assertNotNull(head.getNext());
        assertNotNull(head.getNext().getNext());
        assertNull(head.getNext().getNext().getNext());
        assertEquals("8 --> 9 --> 10", list.toString());
    }

    @Test
    void insertAtTheEnd() throws NoSuchFieldException, IllegalAccessException {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

        DoublyLinkedList.Node<Integer> tail;
        DoublyLinkedList.Node<Integer> head;

        list.insertAtTheEnd(10);
        assertEquals(1, list.size());
        head = getHead(list);
        tail = getTail(list);
        assertEquals(head, tail);
        assertNull(head.getNext());
        assertEquals("10", list.toString());

        list.insertAtTheEnd(9);
        assertEquals(2, list.size());
        head = getHead(list);
        tail = getTail(list);
        assertNotNull(head.getNext());
        assertNull(head.getNext().getNext());
        assertEquals(tail, head.getNext());
        assertEquals("10 --> 9", list.toString());

        list.insertAtTheEnd(8);
        assertEquals(3, list.size());
        head = getHead(list);
        tail = getTail(list);
        assertNotNull(head.getNext());
        assertNotNull(head.getNext().getNext());
        assertNull(head.getNext().getNext().getNext());
        assertEquals(tail, head.getNext().getNext());
        assertEquals("10 --> 9 --> 8", list.toString());

        list.insertAtTheEnd(7);
        assertEquals(4, list.size());
        head = getHead(list);
        tail = getTail(list);
        assertNotNull(head.getNext());
        assertNotNull(head.getNext().getNext());
        assertNotNull(head.getNext().getNext().getNext());
        assertNull(head.getNext().getNext().getNext().getNext());
        assertEquals(tail, head.getNext().getNext().getNext());
        assertEquals("10 --> 9 --> 8 --> 7", list.toString());
    }

    @Test
    void insert() throws NoSuchFieldException, IllegalAccessException {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

        DoublyLinkedList.Node<Integer> tail;
        DoublyLinkedList.Node<Integer> head;

        assertThrows(IllegalArgumentException.class, () -> list.insert(0, 1));

        list.insertAtTheBeginning(10);

        list.insert(0, 9);
        assertEquals(2, list.size());
        head = getHead(list);
        tail = getTail(list);
        assertNotNull(head.getNext());
        assertNull(head.getNext().getNext());
        assertEquals(tail, head.getNext());
        assertEquals("9 --> 10", list.toString());

        list.insert(0, 8);
        assertEquals(3, list.size());
        head = getHead(list);
        tail = getTail(list);
        assertNotNull(head.getNext());
        assertNotNull(head.getNext().getNext());
        assertNull(head.getNext().getNext().getNext());
        assertEquals(tail, head.getNext().getNext());
        assertEquals("8 --> 9 --> 10", list.toString());

        list.insert(0, 7);
        assertEquals(4, list.size());
        head = getHead(list);
        tail = getTail(list);
        assertNotNull(head.getNext());
        assertNotNull(head.getNext().getNext());
        assertNotNull(head.getNext().getNext().getNext());
        assertNull(head.getNext().getNext().getNext().getNext());
        assertEquals(tail, head.getNext().getNext().getNext());
        assertEquals("7 --> 8 --> 9 --> 10", list.toString());

        list.insert(1, 6);
        assertEquals(5, list.size());
        head = getHead(list);
        tail = getTail(list);
        assertNotNull(head.getNext());
        assertNotNull(head.getNext().getNext());
        assertNotNull(head.getNext().getNext().getNext());
        assertNotNull(head.getNext().getNext().getNext().getNext());
        assertNull(head.getNext().getNext().getNext().getNext().getNext());
        assertEquals(tail, head.getNext().getNext().getNext().getNext());
        assertEquals("7 --> 6 --> 8 --> 9 --> 10", list.toString());

        list.insert(4, 11);
        assertEquals(6, list.size());
        head = getHead(list);
        tail = getTail(list);
        assertNotNull(head.getNext());
        assertNotNull(head.getNext().getNext());
        assertNotNull(head.getNext().getNext().getNext());
        assertNotNull(head.getNext().getNext().getNext().getNext());
        assertNotNull(head.getNext().getNext().getNext().getNext().getNext());
        assertNull(head.getNext().getNext().getNext().getNext().getNext().getNext());
        assertEquals(tail, head.getNext().getNext().getNext().getNext().getNext());
        assertEquals("7 --> 6 --> 8 --> 9 --> 10 --> 11", list.toString());

        list.insert(1, 2);
        assertEquals(7, list.size());
        head = getHead(list);
        tail = getTail(list);
        assertNotNull(head.getNext());
        assertNotNull(head.getNext().getNext());
        assertNotNull(head.getNext().getNext().getNext());
        assertNotNull(head.getNext().getNext().getNext().getNext());
        assertNotNull(head.getNext().getNext().getNext().getNext().getNext());
        assertNotNull(head.getNext().getNext().getNext().getNext().getNext().getNext());
        assertNull(head.getNext().getNext().getNext().getNext().getNext().getNext().getNext());
        assertEquals(tail, head.getNext().getNext().getNext().getNext().getNext().getNext());
        assertEquals("7 --> 2 --> 6 --> 8 --> 9 --> 10 --> 11", list.toString());
    }

    @Test
    void removeHead() throws NoSuchFieldException, IllegalAccessException {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

        list.insertAtTheBeginning(10);
        list.insert(0, 9);
        list.insertAtTheEnd(12);
        list.insert(1, 14);
        list.insertAtTheBeginning(1);
        list.insertAtTheEnd(20);
        list.insert(1, 2);
        list.insert(2, 3);
        list.insert(3, 4);

        DoublyLinkedList.Node<Integer> tail;
        DoublyLinkedList.Node<Integer> head;

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
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

        list.insertAtTheBeginning(10);
        list.insert(0, 9);
        list.insertAtTheEnd(12);
        list.insert(1, 14);
        list.insertAtTheBeginning(1);
        list.insertAtTheEnd(20);
        list.insert(1, 2);
        list.insert(2, 3);
        list.insert(3, 4);

        DoublyLinkedList.Node<Integer> tail;
        DoublyLinkedList.Node<Integer> head;

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
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

        list.insertAtTheBeginning(10);
        list.insert(0, 9);
        list.insertAtTheEnd(12);
        list.insert(1, 14);
        list.insertAtTheBeginning(1);
        list.insertAtTheEnd(20);
        list.insert(1, 2);
        list.insert(2, 3);
        list.insert(3, 4);

        DoublyLinkedList.Node<Integer> tail;
        DoublyLinkedList.Node<Integer> head;

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
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

        list.insertAtTheBeginning(10);
        list.insert(0, 9);
        list.insertAtTheEnd(12);
        list.insert(1, 14);
        list.insertAtTheBeginning(1);
        list.insertAtTheEnd(20);
        list.insert(1, 2);
        list.insert(2, 3);
        list.insert(3, 4);

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

        list.printList();
    }

    @Test
    void reverse() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

        list.insertAtTheBeginning(10);
        list.insert(0, 9);
        list.insertAtTheEnd(12);
        list.insert(1, 14);
        list.insertAtTheBeginning(1);
        list.insertAtTheEnd(20);
        list.insert(1, 2);
        list.insert(2, 3);
        list.insert(3, 4);

        assertEquals("1 --> 2 --> 3 --> 4 --> 9 --> 14 --> 10 --> 12 --> 20", list.toString());
        list.reverse();
        assertEquals("20 --> 12 --> 10 --> 14 --> 9 --> 4 --> 3 --> 2 --> 1", list.toString());

        list = new DoublyLinkedList<>();
        assertEquals("", list.toString());
        list.reverse();
        assertEquals("", list.toString());

        list = new DoublyLinkedList<>();
        list.insertAtTheBeginning(10);
        assertEquals("10", list.toString());
        list.reverse();
        assertEquals("10", list.toString());

        list = new DoublyLinkedList<>();
        list.insertAtTheBeginning(10);
        list.insertAtTheBeginning(9);
        assertEquals("9 --> 10", list.toString());
        list.reverse();
        assertEquals("10 --> 9", list.toString());

        list = new DoublyLinkedList<>();
        list.insertAtTheBeginning(10);
        list.insertAtTheBeginning(9);
        list.insertAtTheBeginning(8);
        assertEquals("8 --> 9 --> 10", list.toString());
        list.reverse();
        assertEquals("10 --> 9 --> 8", list.toString());
    }

    private static <E> DoublyLinkedList.Node<E> getHead(DoublyLinkedList<E> numbers) throws NoSuchFieldException, IllegalAccessException {
        Field elementsField = numbers.getClass().getDeclaredField("head");
        elementsField.setAccessible(true);
        return (DoublyLinkedList.Node<E>) elementsField.get(numbers);
    }

    private static <E> DoublyLinkedList.Node<E> getTail(DoublyLinkedList<E> numbers) throws NoSuchFieldException, IllegalAccessException {
        Field elementsField = numbers.getClass().getDeclaredField("tail");
        elementsField.setAccessible(true);
        return (DoublyLinkedList.Node<E>) elementsField.get(numbers);
    }
}