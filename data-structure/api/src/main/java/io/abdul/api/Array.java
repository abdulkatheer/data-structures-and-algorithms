package io.abdul.api;

import io.abdul.api.exception.IndexOutOfBounds;

import java.util.Iterator;
import java.util.Optional;

public interface Array<E> {
    /**
     * Add an element to the array
     * Adds to the end of the array
     *
     * @param element to be added
     * @throws IndexOutOfBounds when Array is full in a static-array implementation
     */
    int add(E element) throws IndexOutOfBounds;

    /**
     * Inserts an element at any position between 0 and size - 1
     * Shifts element from position to right side
     *
     * @param position should be less than size
     * @param element  to be inserted
     * @throws IndexOutOfBounds when position is not between 0 and size - 1 (inclusive)
     */
    void insert(int position, E element) throws IndexOutOfBounds;

    /**
     * Updates element at the given position
     *
     * @param position should be less than size
     * @param element  to be updated
     * @throws IndexOutOfBounds when position is not between 0 and size - 1 (inclusive)
     */
    E set(int position, E element) throws IndexOutOfBounds;

    /**
     * Removes an element at the position
     * Shifts element from position + 1 to the left
     *
     * @param position should be less than size
     * @throws IndexOutOfBounds when position is not between 0 and size - 1 (inclusive)
     */
    E remove(int position) throws IndexOutOfBounds;

    Optional<Integer> lookup(E element);

    /**
     * @param position should be less than size
     * @return element at the given position
     * @throws IndexOutOfBounds when position is not between 0 and size - 1 (inclusive)
     */
    E get(int position) throws IndexOutOfBounds;

    int size();

    Iterator<E> iterator();
}
