package io.abdul;

import io.abdul.api.PriorityQueue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriorityQueueUsingHeapTest {
    @Test
    void testPriorityQueue() {
        testOneElement();

        testTwoElements();

        testManyElements();

        testDuplicates();
    }

    private static void testDuplicates() {
        PriorityQueue<Integer> numbers = new PriorityQueueUsingHeap<>();
        assertEquals(0, numbers.size());

        numbers.enqueue(9);
        numbers.enqueue(10);
        numbers.enqueue(9);
        numbers.enqueue(10);
        numbers.enqueue(9);
        numbers.enqueue(10);
        numbers.enqueue(9);
        numbers.enqueue(10);
        numbers.enqueue(9);
        numbers.enqueue(10);
        numbers.enqueue(9);
        numbers.enqueue(10);
        assertEquals(12, numbers.size());

        assertEquals(10, numbers.dequeue());
        assertEquals(11, numbers.size());

        assertEquals(10, numbers.dequeue());
        assertEquals(10, numbers.size());

        assertEquals(10, numbers.dequeue());
        assertEquals(9, numbers.size());

        assertEquals(10, numbers.dequeue());
        assertEquals(8, numbers.size());

        assertEquals(10, numbers.dequeue());
        assertEquals(7, numbers.size());

        assertEquals(10, numbers.dequeue());
        assertEquals(6, numbers.size());

        assertEquals(9, numbers.dequeue());
        assertEquals(5, numbers.size());

        assertEquals(9, numbers.dequeue());
        assertEquals(4, numbers.size());

        assertEquals(9, numbers.dequeue());
        assertEquals(3, numbers.size());

        assertEquals(9, numbers.dequeue());
        assertEquals(2, numbers.size());

        assertEquals(9, numbers.dequeue());
        assertEquals(1, numbers.size());

        assertEquals(9, numbers.dequeue());
        assertEquals(0, numbers.size());
    }

    private static void testManyElements() {
        PriorityQueue<Integer> numbers = new PriorityQueueUsingHeap<>();
        assertEquals(0, numbers.size());

        numbers.enqueue(9);
        numbers.enqueue(10);
        numbers.enqueue(124);
        numbers.enqueue(2);
        numbers.enqueue(12);
        numbers.enqueue(90);
        numbers.enqueue(14);
        numbers.enqueue(547);
        numbers.enqueue(68);
        numbers.enqueue(74);
        numbers.enqueue(10000);
        assertEquals(11, numbers.size());

        assertEquals(10000, numbers.dequeue());
        assertEquals(10, numbers.size());

        assertEquals(547, numbers.dequeue());
        assertEquals(9, numbers.size());

        assertEquals(124, numbers.dequeue());
        assertEquals(8, numbers.size());

        assertEquals(90, numbers.dequeue());
        assertEquals(7, numbers.size());

        assertEquals(74, numbers.dequeue());
        assertEquals(6, numbers.size());

        assertEquals(68, numbers.dequeue());
        assertEquals(5, numbers.size());

        assertEquals(14, numbers.dequeue());
        assertEquals(4, numbers.size());

        assertEquals(12, numbers.dequeue());
        assertEquals(3, numbers.size());

        assertEquals(10, numbers.dequeue());
        assertEquals(2, numbers.size());

        assertEquals(9, numbers.dequeue());
        assertEquals(1, numbers.size());

        assertEquals(2, numbers.dequeue());
        assertEquals(0, numbers.size());
    }

    private static void testTwoElements() {
        PriorityQueue<Integer> numbers = new PriorityQueueUsingHeap<>();
        assertEquals(0, numbers.size());

        numbers.enqueue(9);
        numbers.enqueue(10);
        assertEquals(2, numbers.size());

        assertEquals(10, numbers.dequeue());
        assertEquals(1, numbers.size());

        assertEquals(9, numbers.dequeue());
        assertEquals(0, numbers.size());
    }

    private static void testOneElement() {
        PriorityQueue<Integer> numbers = new PriorityQueueUsingHeap<>();
        assertEquals(0, numbers.size());

        numbers.enqueue(10);
        assertEquals(1, numbers.size());
        assertEquals(10, numbers.dequeue());
    }
}