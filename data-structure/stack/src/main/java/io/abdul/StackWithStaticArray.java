package io.abdul;


import io.abdul.api.Array;
import io.abdul.api.Stack;
import io.abdul.api.exception.IndexOutOfBounds;
import io.abdul.api.exception.StackOverflow;
import io.abdul.api.exception.StackUnderflow;

import java.util.Iterator;
import java.util.Optional;

/**
 * Fixed Size Stack implementation using StaticArray data structure
 *
 * @param <E>
 */
public class StackWithStaticArray<E> implements Stack<E> {
    private final Array<E> stack;
    private final int capacity;

    public StackWithStaticArray(int size) {
        this.stack = new StaticArray<>(size);
        this.capacity = size;
    }

    @Override
    public void push(E element) {
        try {
            if (stack.size() == 0) {
                stack.add(element);
            } else {
                stack.insert(0, element);
            }
        } catch (IndexOutOfBounds e) {
            throw new StackOverflow("Stack is full");
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
    public boolean isFull() {
        return stack.size() == capacity;
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
