package io.abdul;

import io.abdul.api.LinkedList;
import io.abdul.api.Queue;
import io.abdul.api.exception.StackUnderflow;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QueueWithLinkedListTest {

    @Test
    void enqueue() throws NoSuchFieldException, IllegalAccessException {
        Queue<Integer> numberQueue = new QueueWithLinkedList<>();

        LinkedList<Integer> stack = getQueue(numberQueue);

        numberQueue.enqueue(5);
        assertEquals(1, stack.size());
        assertEquals(0, stack.lookup(5).get());

        numberQueue.enqueue(4);
        assertEquals(2, stack.size());
        assertEquals(0, stack.lookup(5).get());
        assertEquals(1, stack.lookup(4).get());

        numberQueue.enqueue(3);
        assertEquals(3, stack.size());
        assertEquals(0, stack.lookup(5).get());
        assertEquals(1, stack.lookup(4).get());
        assertEquals(2, stack.lookup(3).get());

        numberQueue.enqueue(2);
        assertEquals(4, stack.size());
        assertEquals(0, stack.lookup(5).get());
        assertEquals(1, stack.lookup(4).get());
        assertEquals(2, stack.lookup(3).get());
        assertEquals(3, stack.lookup(2).get());

        numberQueue.enqueue(1);
        assertEquals(5, stack.size());
        assertEquals(0, stack.lookup(5).get());
        assertEquals(1, stack.lookup(4).get());
        assertEquals(2, stack.lookup(3).get());
        assertEquals(3, stack.lookup(2).get());
        assertEquals(4, stack.lookup(1).get());
    }

    @Test
    void dequeue() throws NoSuchFieldException, IllegalAccessException {
        Queue<Integer> numberQueue = new QueueWithLinkedList<>();

        LinkedList<Integer> stack = getQueue(numberQueue);

        numberQueue.enqueue(5);
        assertEquals(1, stack.size());
        assertEquals(0, stack.lookup(5).get());
        assertEquals(5, numberQueue.dequeue());
        assertEquals(0, stack.size());

        numberQueue.enqueue(5);
        numberQueue.enqueue(4);
        numberQueue.enqueue(3);
        assertEquals(3, stack.size());
        assertEquals(3, numberQueue.dequeue());
        assertEquals(2, stack.size());
        assertEquals(4, numberQueue.dequeue());
        assertEquals(1, stack.size());
        assertEquals(5, numberQueue.dequeue());
        assertEquals(0, stack.size());

        assertThrows(StackUnderflow.class, numberQueue::dequeue);
    }

    @Test
    void peek() {
    }

    @Test
    void lookup() {
    }

    @Test
    void isEmpty() {
    }

    @Test
    void isFull() {
    }

    @Test
    void size() {
    }

    @Test
    void iterator() {
    }

    private static <E> LinkedList<E> getQueue(Queue<E> numbers) throws NoSuchFieldException, IllegalAccessException {
        Field elementsField = numbers.getClass().getDeclaredField("queue");
        elementsField.setAccessible(true);
        return (LinkedList<E>) elementsField.get(numbers);
    }
}