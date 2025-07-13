package io.abdul.stack_queue.implementation.problem2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Solutions {

  public static void main(String[] args) {
    // Example 1
    ArrayQueue queue1 = new ArrayQueue();
    queue1.push(5);
    queue1.push(10);
    assertEquals(5, queue1.peek());
    assertEquals(5, queue1.pop());
    assertFalse(queue1.isEmpty());

    // Example 2
    ArrayQueue queue2 = new ArrayQueue();
    assertTrue(queue2.isEmpty());

    // Example 3
    ArrayQueue queue3 = new ArrayQueue();
    queue3.push(1);
    assertEquals(1, queue3.pop());
    assertTrue(queue3.isEmpty());

    // Custom case: multiple pushes and pops
    ArrayQueue queue4 = new ArrayQueue();
    queue4.push(100);
    queue4.push(200);
    queue4.push(300);
    assertEquals(100, queue4.peek());
    assertEquals(100, queue4.pop());
    assertEquals(200, queue4.peek());
    queue4.pop();
    queue4.pop();
    assertTrue(queue4.isEmpty());

    // Custom case: interleaved operations
    ArrayQueue queue5 = new ArrayQueue();
    queue5.push(7);
    assertFalse(queue5.isEmpty());
    assertEquals(7, queue5.peek());
    queue5.push(8);
    assertEquals(7, queue5.peek());
    queue5.pop();
    assertEquals(8, queue5.peek());
  }
}

class ArrayQueue {

  private final int[] queue = new int[100];
  private int start = -1, end = -1, size = 0;

  public ArrayQueue() {
  }

  /*
  T - O(1)
  */
  public void push(int x) {
    if (size == 100) {
      return;
    }

    if (size == 0) { // Empty to 1 case
      start = end = 0;
    } else {
      end = (end + 1) % queue.length;
    }

    queue[end] = x;
    size++;
  }

  /*
  T - O(1)
  */
  public int pop() {
    if (size == 0) {
      return -1;
    }

    int e = queue[start];
    if (size == 1) { // 1 to empty case
      start = end = -1;
    } else {
      start = (start + 1) % queue.length;
    }
    size--;

    return e;
  }

  /*
  T - O(1)
  */
  public int peek() {
    if (size == 0) {
      return -1;
    }

    return queue[start];
  }

  public boolean isEmpty() {
    return size == 0;
  }
}
