package io.abdul;

import io.abdul.api.Heap;
import io.abdul.api.PriorityQueue;
import io.abdul.api.exception.OperationNotSupported;
import io.abdul.api.exception.QueueOverflow;
import io.abdul.api.exception.QueueUnderflow;

import java.util.Comparator;
import java.util.Iterator;

public class PriorityQueueUsingHeap<E extends Comparable<E>> implements PriorityQueue<E> {
    private final Heap<E> heap;

    public PriorityQueueUsingHeap() {
        this.heap = new HeapUsingDynamicArray<>();
    }

    public PriorityQueueUsingHeap(Comparator<E> comparator) {
        this.heap = new HeapUsingDynamicArray<>(comparator);
    }

    @Override
    public void enqueue(E element) throws QueueOverflow {
        heap.insert(element);
    }

    @Override
    public E dequeue() throws QueueUnderflow {
        return heap.delete();
    }

    @Override
    public E peek() throws QueueUnderflow {
        return heap.delete();
    }

    @Override
    public boolean isEmpty() {
        return heap.size() == 0;
    }

    @Override
    public boolean isFull() throws OperationNotSupported {
        throw new OperationNotSupported("isFull is not supported in " + PriorityQueueUsingHeap.class.getName());
    }

    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public Iterator<E> iterator() {
        throw new OperationNotSupported("iterator is not supported in " + PriorityQueueUsingHeap.class.getName());
    }
}
