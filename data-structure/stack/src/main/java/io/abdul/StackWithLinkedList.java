package io.abdul;

import io.abdul.api.Stack;
import io.abdul.api.exception.OperationNotSupported;
import io.abdul.api.exception.StackOverflow;
import io.abdul.api.exception.StackUnderflow;

import java.util.Iterator;
import java.util.Optional;

/**
 * Dynamic Size Stack implementation using Singly Linked List data structure
 *
 * @param <E>
 */
public class StackWithLinkedList<E> implements Stack<E> {
    @Override
    public void push(E element) throws StackOverflow {

    }

    @Override
    public E pop() throws StackUnderflow {
        return null;
    }

    @Override
    public E peek() throws StackUnderflow {
        return null;
    }

    @Override
    public Optional<Integer> lookup(E element) {
        return Optional.empty();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isFull() throws OperationNotSupported {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }
}
