package io.abdul.stack;

import io.abdul.SinglyLinkedList;
import io.abdul.api.LinkedList;
import io.abdul.api.Stack;
import io.abdul.api.exception.IndexOutOfBounds;
import io.abdul.api.exception.OperationNotSupported;
import io.abdul.api.exception.StackUnderflow;

import java.util.Iterator;
import java.util.Optional;

/**
 * Dynamic Size Stack implementation using Singly Linked List data structure
 *
 * @param <E>
 */
public class StackWithLinkedList<E> implements Stack<E> {
    private final LinkedList<E> stack;

    public StackWithLinkedList() {
        stack = new SinglyLinkedList<>();
    }

    @Override
    public void push(E element) {
        stack.insertAtTheBeginning(element);
    }

    @Override
    public E pop() throws StackUnderflow {
        try {
            return stack.removeFirstElement();
        } catch (IndexOutOfBounds e) {
            throw new StackUnderflow("Stack is empty");
        }
    }

    @Override
    public E peek() throws StackUnderflow {
        try {
            return stack.getFirstElement();
        } catch (IndexOutOfBounds e) {
            throw new StackUnderflow("Stack is empty");
        }
    }

    @Override
    public Optional<Integer> lookup(E element) {
        return stack.lookup(element);
    }

    @Override
    public boolean isEmpty() {
        return stack.size() == 0;
    }

    @Override
    public boolean isFull() throws OperationNotSupported {
        throw new OperationNotSupported("isFull is not supported in StackWithLinkedList");
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }
}
