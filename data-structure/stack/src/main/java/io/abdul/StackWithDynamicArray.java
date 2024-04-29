package io.abdul;

import io.abdul.api.Array;
import io.abdul.api.Stack;
import io.abdul.api.exception.OperationNotSupported;
import io.abdul.api.exception.StackUnderflow;

import java.util.Iterator;
import java.util.Optional;

/**
 * Dynamic Size Stack implementation using DynamicArray data structure
 *
 * @param <E>
 */
public class StackWithDynamicArray<E> implements Stack<E> {
    private final Array<E> stack;

    public StackWithDynamicArray() {
        this.stack = new DynamicArray<>();
    }

    @Override
    public void push(E element) {
        if (stack.size() == 0) {
            stack.add(element);
        } else {
            stack.insert(0, element);
        }
    }

    @Override
    public E pop() throws StackUnderflow {
        if (stack.size() == 0) {
            throw new StackUnderflow("Stack is empty");
        }
        return stack.remove(0);
    }

    @Override
    public E peek() throws StackUnderflow {
        if (stack.size() == 0) {
            throw new StackUnderflow("Stack is empty");
        }
        return stack.get(0);
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
        throw new OperationNotSupported("isFull is not supported in StackWithDynamicArray");
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
