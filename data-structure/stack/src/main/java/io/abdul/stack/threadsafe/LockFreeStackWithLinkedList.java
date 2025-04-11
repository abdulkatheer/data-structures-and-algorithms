package io.abdul.stack.threadsafe;

import io.abdul.api.Stack;
import io.abdul.api.exception.StackOverflow;
import io.abdul.api.exception.StackUnderflow;

import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;

public class LockFreeStackWithLinkedList<E> implements Stack<E> {
    private final ConcurrentLinkedQueue<Integer> pushRetries = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Integer> popRetries = new ConcurrentLinkedQueue<>();

    private final AtomicReference<StackState<E>> state;
    private final int maxSize;

    public LockFreeStackWithLinkedList(int maxSize) {
        this.maxSize = maxSize;
        this.state = new AtomicReference<>(new StackState<>(null, 0));
    }

    public LockFreeStackWithLinkedList() {
        this(-1);
    }

    @Override
    public void push(E value) {
        if (!_push(value)) {
            throw new StackOverflow("Stack is full");
        }
    }

    @Override
    public E pop() {
        E e = _pop();
        if (e == null) {
            throw new StackUnderflow("No elements in stack");
        }
        return e;
    }

    @Override
    public E peek() {
        StackState<E> currentState = state.get();
        return currentState.top != null ? currentState.top.value : null;
    }

    @Override
    public Optional<Integer> lookup(E element) {
        return Optional.empty();
    }

    public boolean isEmpty() {
        return state.get().top == null;
    }

    @Override
    public int size() {
        return state.get().size;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public boolean isFull() {
        return maxSize != -1 && state.get().size >= maxSize;
    }

    public ConcurrentLinkedQueue<Integer> getPushRetryCounts() {
        return pushRetries;
    }

    public ConcurrentLinkedQueue<Integer> getPopRetryCounts() {
        return popRetries;
    }

    private boolean _push(E value) {
        boolean pushed = false;
        int retries = 0;
        while (true) {
            StackState<E> currentState = state.get();
            // Issue: If Thread is suspended at this line and some other thread pop(s), stack will have room to add. But next statement fill fail anyways!

            // Check if the stack is full
            if (maxSize != -1 && currentState.size >= maxSize) {
                break;
            }

            // Create a new node and a new state
            StackNode<E> newNode = new StackNode<>(value);
            newNode.next = currentState.top; // Link the new node to the current top
            StackState<E> newState = new StackState<>(newNode, currentState.size + 1); // a new state, so no ABA problem

            // Attempt to update the stack state atomically
            // All above operations are dependent on the currentState we had initially
            // So if that's changed, our logic has to restart!
            if (state.compareAndSet(currentState, newState)) {
                pushed = true; // Push successful
                break;
            } else {
                retries++;
            }
        }
        pushRetries.add(retries); // Record retries for this operation
        return pushed;
    }

    private E _pop() {
        int retries = 0;
        E element = null;
        while (true) {
            StackState<E> currentState = state.get();
            // Issue: If thread suspends here, and if other threads able to push to stack, the next statement will fail anyways!

            // Check if the stack is empty
            if (currentState.top == null) {
                break;
            }

            // Create a new state with the top removed
            StackNode<E> newTop = currentState.top.next;
            StackState<E> newState = new StackState<>(newTop, currentState.size - 1); // a new state, so no ABA problem

            // Attempt to update the stack state atomically
            // All above operations are dependent on the currentState we had initially
            // So if that's changed, our logic has to restart!
            if (state.compareAndSet(currentState, newState)) {
                element = currentState.top.value; // Return the popped value
                break;
            } else {
                retries++;
            }
        }
        popRetries.add(retries);
        return element;
    }

    private static class StackNode<T> {
        public T value;
        public StackNode<T> next;

        public StackNode(T value) {
            this.value = value;
        }
    }

    // Wrapper class to hold both the top of the stack and the size
    // Immutable, so top and size are always changed together
    private static class StackState<T> {
        private final StackNode<T> top;
        private final int size;

        StackState(StackNode<T> top, int size) {
            this.top = top;
            this.size = size;
        }
    }
}