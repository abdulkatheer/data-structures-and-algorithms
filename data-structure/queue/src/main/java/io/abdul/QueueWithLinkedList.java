package io.abdul;


import io.abdul.api.LinkedList;
import io.abdul.api.Queue;
import io.abdul.api.exception.IndexOutOfBounds;
import io.abdul.api.exception.OperationNotSupported;
import io.abdul.api.exception.QueueUnderflow;

import java.util.Iterator;
import java.util.Optional;

public class QueueWithLinkedList<E> implements Queue<E> {
    private final LinkedList<E> queue;

    public QueueWithLinkedList() {
        this.queue = new SinglyLinkedList<>();
    }

    /**
     * {@inheritDoc}
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    @Override
    public void enqueue(E element) {
        queue.insertAtTheEnd(element);
    }

    /**
     * {@inheritDoc}
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    @Override
    public E dequeue() throws QueueUnderflow {
        try {
            return queue.removeFirstElement();
        } catch (IndexOutOfBounds e) {
            throw new QueueUnderflow("Queue is empty");
        }
    }

    /**
     * {@inheritDoc}
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    @Override
    public E peek() throws QueueUnderflow {
        try {
            return queue.getFirstElement();
        } catch (IndexOutOfBounds e) {
            throw new QueueUnderflow("Queue is empty");
        }
    }

    /**
     * {@inheritDoc}
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    @Override
    public Optional<Integer> lookup(E element) {
        return queue.lookup(element);
    }

    /**
     * {@inheritDoc}
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    @Override
    public boolean isEmpty() {
        return queue.size() == 0;
    }

    /**
     * {@inheritDoc}
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    @Override
    public boolean isFull() throws OperationNotSupported {
        throw new OperationNotSupported("isFull is not supported in QueueWithLinkedList");
    }

    /**
     * {@inheritDoc}
     *
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }
}
