package io.abdul.stack_queue.implementation.problem3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
Moving elements from Queue to another Queue will not change ordering!
 */
public class Solutions {

  public static void main(String[] args) {
    // Example 1
    QueueStack stack1 = new QueueStack();
    stack1.push(5);
    stack1.push(10);
    assertEquals(10, stack1.top());
    assertEquals(10, stack1.pop());
    assertFalse(stack1.isEmpty());

    // Example 2
    QueueStack stack2 = new QueueStack();
    assertTrue(stack2.isEmpty());
    stack2.push(1);
    assertEquals(1, stack2.pop());
    assertTrue(stack2.isEmpty());

    // Example 3
    QueueStack stack3 = new QueueStack();
    assertTrue(stack3.isEmpty());

    // Custom case: multiple pushes and pops
    QueueStack stack4 = new QueueStack();
    stack4.push(100);
    stack4.push(200);
    stack4.push(300);
    assertEquals(300, stack4.top());
    assertEquals(300, stack4.pop());
    assertEquals(200, stack4.top());
    stack4.pop();
    stack4.pop();
    assertTrue(stack4.isEmpty());

    // Custom case: interleaved operations
    QueueStack stack5 = new QueueStack();
    stack5.push(7);
    assertFalse(stack5.isEmpty());
    assertEquals(7, stack5.top());
    stack5.push(8);
    assertEquals(8, stack5.top());
    stack5.pop();
    assertEquals(7, stack5.top());
  }
}

class QueueStack {

  private final ArrayQueue queue = new ArrayQueue();

  public QueueStack() {
  }

  /*
  We need the new element to be at front.
  1) Add new element to the back
  2) Remove all elements from front and add it behind the new element 1 by 1

  Add 1
  [1]

  Add 2
  [1,2]
  [2,1]

  Add 3
  [2,1,3]
  [3,2,1]

  Add 4
  [3,2,1,4]
  [4,3,2,1]
   */
  public void push(int x) {
    if (queue.isFull()) {
      return;
    }

    int size = queue.getSize();
    queue.push(x);

    for (int i = 0; i < size; i++) {
      queue.push(queue.pop());
    }
  }

  public int pop() {
    return queue.pop();
  }

  public int top() {
    return queue.peek();
  }

  public boolean isEmpty() {
    return queue.isEmpty();
  }
}

class ArrayQueue {

  private final int[] queue = new int[100];
  private int start = -1, end = -1, size = 0;

  public ArrayQueue() {
  }

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

  public int peek() {
    if (size == 0) {
      return -1;
    }

    return queue[start];
  }

  public boolean isEmpty() {
    return size == 0;
  }

  boolean isFull() {
    return size == queue.length;
  }

  int getSize() {
    return size;
  }
}