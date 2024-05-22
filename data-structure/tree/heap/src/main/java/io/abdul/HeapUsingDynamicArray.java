package io.abdul;

import io.abdul.api.Heap;
import io.abdul.api.exception.HeapEmpty;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * position - starts from 1, NOT 0
 *
 * @param <E>
 */
public class HeapUsingDynamicArray<E extends Comparable<E>> implements Heap<E> {
    private final List<E> elements;
    private final Comparator<E> comparator;
    private int size;

    public HeapUsingDynamicArray() {
        this.comparator = null;
        elements = new ArrayList<>();
    }

    public HeapUsingDynamicArray(Comparator<E> comparator) {
        this.comparator = comparator;
        elements = new ArrayList<>();
    }

    public HeapUsingDynamicArray(List<E> unorderedBinaryTree) {
        if (unorderedBinaryTree.isEmpty()) {
            throw new IllegalArgumentException("Empty tree");
        }
        this.comparator = null;
        this.size = unorderedBinaryTree.size();
        buildHeap(unorderedBinaryTree);
        this.elements = unorderedBinaryTree; // ordered now
    }

    public HeapUsingDynamicArray(List<E> unorderedBinaryTree, Comparator<E> comparator) {
        if (unorderedBinaryTree.isEmpty()) {
            throw new IllegalArgumentException("Empty tree");
        }
        this.comparator = comparator;
        this.size = unorderedBinaryTree.size();
        buildHeap(unorderedBinaryTree, comparator);
        this.elements = unorderedBinaryTree; // ordered now
    }

    /**
     * Approach 2 - add at the end and heapify bottom-up
     *
     * @param element
     */
    @Override
    public void insert(E element) {
        elements.add(element);
        if (comparator != null) {
            fixBottomUp(elements, comparator, elements.size());
        } else {
            fixBottomUp(elements, elements.size());
        }
        size++;
    }

    @Override
    public E peek() {
        if (size == 0) {
            throw new HeapEmpty("No elements in the heap");
        }
        return elements.get(0);
    }

    @Override
    public E delete() {
        if (size == 0) {
            throw new HeapEmpty("No elements in the heap");
        }
        E topElement = elements.get(0);
        elements.set(0, elements.get(size - 1)); // Copying last element to first
        elements.remove(size - 1);
        if (comparator != null) { // will recursively resolve heap property
            heapify(elements, comparator, elements.size(), 1);
        } else {
            heapify(elements, elements.size(), 1);
        }
        size--;
        return topElement;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Top-bottom approach
     * In-place heapify
     * O(log n)
     */
    private static <E extends Comparable<E>> void fixBottomUp(List<E> elements, int position) {
        int parent = position / 2;
        if (parent >= 1 && elements.get(parent - 1).compareTo(elements.get(position - 1)) < 0) { // parent is bigger
            E temp = elements.get(position - 1);
            elements.set(position - 1, elements.get(parent - 1));
            elements.set(parent - 1, temp);
            fixBottomUp(elements, parent); // Recursive case
        }
        // Base case, returns silently
    }

    /**
     * Top-bottom approach
     * In-place heapify
     * O(log n)
     */
    private static <E extends Comparable<E>> void fixBottomUp(List<E> elements, Comparator<E> comparator, int position) {
        int parent = position / 2;
        if (parent >= 1 && comparator.compare(elements.get(parent - 1), elements.get(position - 1)) < 0) { // parent is bigger
            E temp = elements.get(position - 1);
            elements.set(position - 1, elements.get(parent - 1));
            elements.set(parent - 1, temp);
            fixBottomUp(elements, comparator, parent); // Recursive case
        }
        // Base case, returns silently
    }

    /**
     * From the bottom to top, (from the lowest parent to the root)
     * Heapifying each element
     * By looking at it, time complexity seems to be O(n log n), but it's actually O(n)
     * Refer "Introduction to Algorithms" book
     *
     * @param elements
     * @param <E>
     */
    private static <E extends Comparable<E>> void buildHeap(List<E> elements) {
        int lastParent = elements.size() / 2;
        for (int i = lastParent; i >= 1; i--) {
            heapify(elements, elements.size(), i);
        }
    }

    /**
     * Top-bottom approach
     * In-place heapify
     * O(log n)
     */
    private static <E extends Comparable<E>> void heapify(List<E> elements, int heapSize, int position) {
        int leftChildPosition = 2 * position;
        int rightChildPosition = 2 * position + 1;

        int toBeReplaced = position;
        if (leftChildPosition <= heapSize && elements.get(leftChildPosition - 1).compareTo(elements.get(toBeReplaced - 1)) > 0) { // if left is bigger
            toBeReplaced = leftChildPosition;
        }
        if (rightChildPosition <= heapSize && elements.get(rightChildPosition - 1).compareTo(elements.get(toBeReplaced - 1)) > 0) { // if right is bigger
            toBeReplaced = rightChildPosition;
        }

        if (toBeReplaced != position) { // need to swap and heapify
            E temp = elements.get(position - 1);
            elements.set(position - 1, elements.get(toBeReplaced - 1));
            elements.set(toBeReplaced - 1, temp);
            heapify(elements, heapSize, toBeReplaced); // Recursive case
        }
        // base case - silently terminates
    }

    /**
     * From the bottom to top, (from the lowest parent to the root)
     * Heapifying each element
     * By looking at it, time complexity seems to be O(n log n), but it's actually O(n)
     * Refer "Introduction to Algorithms" book
     *
     * @param elements
     * @param <E>
     */
    private static <E extends Comparable<E>> void buildHeap(List<E> elements, Comparator<E> comparator) {
        int lastParent = elements.size() / 2;
        for (int i = lastParent; i >= 1; i--) {
            heapify(elements, comparator, elements.size(), i);
        }
    }

    /**
     * Top-bottom approach
     * In-place heapify
     * O(log n)
     */
    private static <E extends Comparable<E>> void heapify(List<E> elements, Comparator<E> comparator, int heapSize, int position) {
        int leftChildPosition = 2 * position;
        int rightChildPosition = 2 * position + 1;

        int largestPosition = position;
        if (leftChildPosition <= heapSize && comparator.compare(elements.get(leftChildPosition - 1), elements.get(largestPosition - 1)) > 0) { // if left is bigger
            largestPosition = leftChildPosition;
        }
        if (rightChildPosition <= heapSize && comparator.compare(elements.get(rightChildPosition - 1), elements.get(largestPosition - 1)) > 0) { // if right is bigger
            largestPosition = rightChildPosition;
        }

        if (largestPosition != position) { // need to swap and heapify
            E temp = elements.get(position - 1);
            elements.set(position - 1, elements.get(largestPosition - 1));
            elements.set(largestPosition - 1, temp);
            heapify(elements, comparator, heapSize, largestPosition); // Recursive case
        }
        // base case - silently terminates
    }
}
