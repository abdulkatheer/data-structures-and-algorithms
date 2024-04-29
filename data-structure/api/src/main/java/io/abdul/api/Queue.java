package io.abdul.api;

import io.abdul.api.exception.OperationNotSupported;
import io.abdul.api.exception.QueueOverflow;
import io.abdul.api.exception.QueueUnderflow;

import java.util.Iterator;
import java.util.Optional;

public interface Queue<E> {
    void enqueue(E element) throws QueueOverflow;

    E dequeue() throws QueueUnderflow;

    E peek() throws QueueUnderflow;

    Optional<Integer> lookup(E element);

    boolean isEmpty();

    /**
     * Checks whether a Queue is full. Applicable only for a fixed size Queue
     * @return true if Queue is full or false otherwise
     * @throws OperationNotSupported when Queue is not a fixed-size Queue
     */
    boolean isFull() throws OperationNotSupported;

    int size();

    Iterator<E> iterator();
}
