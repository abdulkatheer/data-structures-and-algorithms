package io.abdul;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class SinglyLinkedList<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size = 0;

    public int search(E element) {
        if (head == null) {
            return -1;
        }

        Node<E> e = head;
        int i = 0;
        while (e != null) {
            if (e.value == element || e.value.equals(element)) {
                return i;
            }
            e = e.next;
            i++;
        }
        return -1;
    }

    public void insertAtTheBeginning(E element) {
        if (head == null) { // empty list
            head = new Node<>(element, null);
            tail = head;
        } else {
            head = new Node<>(element, head);
        }
        size++;
    }

    public void insertAtTheEnd(E element) {
        Node<E> newTail = new Node<>(element, null);
        if (size == 0) { // empty list
            head = newTail;
            tail = newTail;
            size++;
        } else { // At least 1 element exists
            tail.next = newTail;
            tail = newTail;
            size++;
        }
    }

    public void insertInTheMiddle(int position, E element) {
        validatePosition(position);

        if (position == 0) {
            insertAtTheBeginning(element);
            return;
        }

        if (position == size - 1) {
            insertAtTheEnd(element);
            return;
        }

        Node<E> e = head;
        int i = 0;
        while (i < size) {
            if (i == position - 1) {
                e.next = new Node<>(element, e.next);
                break;
            }
            e = e.next;
            i++;
        }
        size++;
    }


    public E removeHead() {
        if (size == 0) {
            throw new IllegalArgumentException("Collection is empty");
        }
        if (size == 1) {
            E e = head.value;
            head = null;
            tail = null;
            size--;
            return e;
        }
        E e = head.value;
        head = head.next;
        size--;
        return e;
    }

    public E removeTail() {
        if (size == 0) {
            throw new IllegalArgumentException("Collection is empty");
        }
        if (size == 1) {  // only 1 element, head and tail are same, after removing both head and tail will be null
            E e = tail.value;
            head = null;
            tail = null;
            size--;
            return e;
        }
        int i = 0;
        Node<E> e = head;
        while (i < size) {
            if (i == size - 2) { // Last to last element
                E v = e.next.value;
                e.next = null;
                tail = e;
                size--;
                return v;
            }
            e = e.next;
            i++;
        }
        throw new IllegalArgumentException("Unable to find");
    }

    public E removeAt(int position) {
        validatePosition(position);

        if (position == 0) { // only 1 element, head and tail are same, after removing both head and tail will be null
            return removeHead();
        }
        if (position == size - 1) {
            return removeTail();
        }

        Node<E> e = head;
        int i = 0;
        while (e != null) {
            if (i == position - 1) {
                Node<E> toBeRemoved = e.next;
                e.next = toBeRemoved.next;
                size--;
                return toBeRemoved.value;
            }
            e = e.next;
            i++;
        }
        throw new IllegalArgumentException("Index our of bounds");
    }

    public E get(int position) {
        validatePosition(position);

        Node<E> e = head;
        int i = 0;
        while (e != null) {
            if (i == position) {
                return e.value;
            }
            e = e.next;
            i++;
        }
        throw new IllegalArgumentException("Index our of bounds");
    }

    public int size() {
        return size;
    }

    public void printList() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        List<E> l = new ArrayList<>();
        Node<E> e = head;
        while (e != null) {
            l.add(e.value);
            e = e.next;
        }
        return l.stream()
                .map(E::toString)
                .collect(joining(" --> "));
    }

    private void validatePosition(int position) {
        if (position < 0) {
            throw new IllegalArgumentException("Position should be zero or higher");
        } else if (position >= size) {
            throw new IllegalArgumentException("Index our of bounds");
        }
    }

    public static class Node<E> {
        private final E value;
        private Node<E> next;

        public Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
        }

        public E getValue() {
            return value;
        }

        public Node<E> getNext() {
            return next;
        }
    }
}
