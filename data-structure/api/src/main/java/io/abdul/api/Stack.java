package io.abdul.api;

import io.abdul.api.exception.OperationNotSupported;
import io.abdul.api.exception.StackOverflow;
import io.abdul.api.exception.StackUnderflow;

import java.util.Iterator;
import java.util.Optional;

public interface Stack<E> {
    /**
     * @param element to be added to stack, adds on top of all other elements
     * @throws StackOverflow when stack is full for a Fixes Size stack
     */
    void push(E element) throws StackOverflow;

    /**
     * Removes and returns the top most element in the stack
     *
     * @return the element which is on top of the stack
     * @throws StackUnderflow when stack is empty
     */
    E pop() throws StackUnderflow;

    /**
     * Returns the top most element in the stack
     *
     * @return the element which is on top of the stack
     * @throws StackUnderflow when stack is empty
     */
    E peek() throws StackUnderflow;

    Optional<E> lookup(E element);

    int isEmpty();

    /**
     * Checks whether a stack is full. Applicable only for a fixed size stack
     * @return true if stack is full or false otherwise
     * @throws OperationNotSupported when stack is not a fixed-size stack
     */
    boolean isFull() throws OperationNotSupported;

    int size();

    Iterator<E> iterator();
}
