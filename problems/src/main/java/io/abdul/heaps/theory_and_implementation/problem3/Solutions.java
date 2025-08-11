package io.abdul.heaps.theory_and_implementation.problem3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class Solutions {

  public static void main(String[] args) {
    Solution heap = new Solution();

    // === First Example ===
    heap.initializeHeap();
    heap.insert(4);
    heap.insert(1);
    heap.insert(10);
    assertEquals(1, heap.getMin(), "Expected min to be 1");
    assertEquals(3, heap.heapSize(), "Heap size should be 3");
    assertFalse(heap.isEmpty(), "Heap should not be empty");
    heap.extractMin();  // remove 1 → heap becomes [4, 10]
    heap.changeKey(0, 16); // [16, 10] → after heapify: [10, 16]
    assertEquals(10, heap.getMin(), "Expected min to be 10");

    // === Second Example ===
    heap = new Solution();
    heap.initializeHeap();
    heap.insert(4);
    heap.insert(1);
    heap.extractMin(); // removes 1 → [4]
    assertEquals(4, heap.getMin(), "Expected min to be 4");
    heap.insert(1); // [1, 4]
    assertEquals(2, heap.heapSize(), "Heap size should be 2");
    assertFalse(heap.isEmpty(), "Heap should not be empty");
    heap.extractMin(); // removes 1 → [4]
    heap.changeKey(0, 2); // [2]
    assertEquals(2, heap.getMin(), "Expected min to be 2");

    // === Edge Case ===
    heap = new Solution();
    heap.initializeHeap();
    assertTrue(heap.isEmpty(), "New heap should be empty");
    heap.insert(-100);
    assertEquals(-100, heap.getMin(), "Expected min to be -100");
    heap.extractMin();
    assertTrue(heap.isEmpty(), "Heap should be empty after extracting only element");
  }
}

class Solution {

  private List<Integer> heap;

  public void initializeHeap() {
    heap = new ArrayList<>();
  }

  public void insert(int key) {
    heap.add(key);
    heapifyUp(heap.size() - 1);
  }

  public void changeKey(int index, int newVal) {
    if (newVal > heap.get(index)) {
      heap.set(index, newVal);
      heapifyDown(index);
    } else {
      heap.set(index, newVal);
      heapifyUp(index);
    }
  }

  public void extractMin() {
    heap.set(0, heap.get(heap.size() - 1));
    heap.remove(heap.size() - 1);
    heapifyDown(0);
  }

  public boolean isEmpty() {
    return heap.isEmpty();
  }

  public int getMin() {
    return heap.isEmpty() ? -1 : heap.get(0);
  }

  public int heapSize() {
    return heap.size();
  }

  private void heapifyUp(int index) {
    while (true) {
      int parent = (index + 1) / 2 - 1;
      if (parent >= 0 && heap.get(parent) > heap.get(index)) {
        int temp = heap.get(parent);
        heap.set(parent, heap.get(index));
        heap.set(index, temp);
        index = parent;
      } else {
        break;
      }
    }
  }

  private void heapifyDown(int index) {
    while (true) {
      int left = 2 * index + 1;
      int right = 2 * index + 2;

      if (left < heap.size() && heap.get(left) < heap.get(index) && right < heap.size()
          && heap.get(right) < heap.get(index)) {
        if (heap.get(left) < heap.get(right)) {
          int temp = heap.get(left);
          heap.set(left, heap.get(index));
          heap.set(index, temp);
          index = left;
        } else {
          int temp = heap.get(right);
          heap.set(right, heap.get(index));
          heap.set(index, temp);
          index = right;
        }
      } else if (left < heap.size() && heap.get(left) < heap.get(index)) {
        int temp = heap.get(left);
        heap.set(left, heap.get(index));
        heap.set(index, temp);
        index = left;
      } else if (right < heap.size() && heap.get(right) < heap.get(index)) {
        int temp = heap.get(right);
        heap.set(right, heap.get(index));
        heap.set(index, temp);
        index = right;
      } else {
        break;
      }
    }
  }
}