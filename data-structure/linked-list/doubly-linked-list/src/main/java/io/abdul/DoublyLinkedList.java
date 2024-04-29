package io.abdul;

import io.abdul.api.LinkedList;
import io.abdul.api.exception.IndexOutOfBounds;
import io.abdul.api.exception.NotImplemented;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

public class DoublyLinkedList<E> implements LinkedList<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size = 0;

    @Override
    public Optional<Integer> lookup(E element) {
        if (head == null) {
            return Optional.empty();
        }

        Node<E> e = head;
        int i = 0;
        while (e != null) {
            if (e.value == element || e.value.equals(element)) {
                return Optional.of(i);
            }
            e = e.next;
            i++;
        }
        return Optional.empty();
    }

    @Override
    public Iterator<E> iterator() {
        throw new NotImplemented("iterator not implemented in DoublyLinkedList");
    }

    @Override
    public void insertAtTheBeginning(E element) {
        Node<E> newHead = new Node<>(element);
        if (head == null) { // empty list
            tail = newHead;
        } else {
            Node<E> currentHead = head;
            newHead.next = currentHead; // Two-way linking
            currentHead.prev = newHead;
        }
        head = newHead;
        size++;
    }

    @Override
    public void insertAtTheEnd(E element) {
        Node<E> newTail = new Node<>(element);
        if (size == 0) { // empty list
            head = newTail;
        } else { // At least 1 element exists
            Node<E> currentTail = tail;
            currentTail.next = newTail;
            newTail.prev = currentTail;
        }
        tail = newTail;
        size++;
    }

    @Override
    public void insert(int position, E element) {
        validatePosition(position);

        if (position == 0) {
            insertAtTheBeginning(element);
        } else if (position == size - 1) {
            insertAtTheEnd(element);
        } else {
            insertAtTheMiddle(position, element);
        }
    }

    @Override
    public E removeFirstElement() {
        if (size == 0) {
            throw new IllegalArgumentException("Collection is empty");
        }
        E value;
        if (size == 1) {
            value = head.value;
            head = null;
            tail = null;
        } else {
            value = head.value;
            Node<E> follower = head.next; // Node next to removing position
            follower.prev = null;
            head = follower;
        }
        size--;
        return value;
    }

    @Override
    public E removeLastElement() {
        if (size == 0) {
            throw new IllegalArgumentException("Collection is empty");
        }
        E value = tail.value;
        if (size == 1) {  // only 1 element, head and tail are same, after removing both head and tail will be null
            head = null;
            tail = null;
        } else { // only 2 elements, after removing, head and tail will be same
            Node<E> leader = tail.prev;  // Node next to removing position
            leader.next = null; // Making leader as new tail
            tail = leader;
        }
        size--;
        return value;
    }

    @Override
    public E getFirstElement() {
        if (size == 0) {
            throw new IndexOutOfBounds("List is empty");
        }
        return head.value;
    }

    @Override
    public E getLastElement() {
        if (size == 0) {
            throw new IndexOutOfBounds("List is empty");
        }
        return tail.value;
    }

    @Override
    public E remove(int position) {
        validatePosition(position);

        if (position == 0) { // only 1 element, head and tail are same, after removing both head and tail will be null
            return removeFirstElement();
        }
        if (position == size - 1) {
            return removeLastElement();
        }

        return removeAtTheMiddle(position);
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

    public void reverse() {
        /*
        null <-- n0 <--> n1 <--> n2 <--> n3 --> null
        null <-- n3 <--> n2 <--> n1 <--> n0 --> null

        null    <-- n0 --> n1       | n1    <-- n0 --> null
        n0      <-- n1 --> n2       | n2    <-- n1 --> n0
        n1      <-- n2 --> n3       | n3    <-- n2 --> n1
        n2      <-- n3 --> null     | null  <-- n3 --> n2

        Just swap prev and next link in each element

         */
        Node<E> prevHead = head;
        head = tail;
        tail = prevHead;

        Node<E> current = prevHead;
        while (current != null) {
            Node<E> currentNext = current.next;
            Node<E> currentPrev = current.prev;
            current.next = currentPrev;
            current.prev = currentNext;
            current = currentNext;
        }
    }

    @Override
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

    public static class Node<E> {
        private final E value;
        private Node<E> prev;
        private Node<E> next;

        public Node(E value) {
            this.value = value;
        }

        public Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
        }

        public Node(E value, Node<E> prev, Node<E> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

        public E getValue() {
            return value;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public Node<E> getNext() {
            return next;
        }
    }

    private void insertAtTheMiddle(int position, E element) {
        Node<E> newNode = new Node<>(element);
        Node<E> e = head;
        int i = 0;
        while (i < size) {
            if (i == position - 1) {
                Node<E> leaderNode = e; // Node before inserting position
                Node<E> followerNode = leaderNode.next; // Node next to inserting position
                leaderNode.next = newNode; // Two-way linking leader and new node
                newNode.prev = leaderNode;

                newNode.next = followerNode; // Two-way linking follower and new node
                followerNode.prev = newNode;
                break;
            }
            e = e.next;
            i++;
        }
        size++;
    }

    private E removeAtTheMiddle(int position) {
        E value = null;
        Node<E> e = head;
        int i = 0;
        while (e != null) {
            if (i == position) {
                Node<E> currentNode = e;
                Node<E> leader = e.prev;
                Node<E> follower = e.next;

                leader.next = follower; // Two-way linking leader and follower node
                follower.prev = leader;
                value = currentNode.value;
                break;
            }
            e = e.next;
            i++;
        }
        size--;
        return value;
    }

    private void validatePosition(int position) {
        if (position < 0) {
            throw new IllegalArgumentException("Position should be zero or higher");
        } else if (position >= size) {
            throw new IllegalArgumentException("Index our of bounds");
        }
    }
}
