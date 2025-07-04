package io.abdul.stack_queue.implementation.problem6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Solutions {

  public static void main(String[] args) {
    // Example 1
    LinkedListQueue queue1 = new LinkedListQueue();
    queue1.push(5);
    queue1.push(10);
    assertEquals(5, queue1.peek());
    assertEquals(5, queue1.pop());
    assertFalse(queue1.isEmpty());

    // Example 2
    LinkedListQueue queue2 = new LinkedListQueue();
    assertTrue(queue2.isEmpty());

    // Example 3
    LinkedListQueue queue3 = new LinkedListQueue();
    queue3.push(1);
    assertEquals(1, queue3.pop());
    assertTrue(queue3.isEmpty());

    // Custom case: multiple pushes and pops
    LinkedListQueue queue4 = new LinkedListQueue();
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
    LinkedListQueue queue5 = new LinkedListQueue();
    queue5.push(7);
    assertFalse(queue5.isEmpty());
    assertEquals(7, queue5.peek());
    queue5.push(8);
    assertEquals(7, queue5.peek());
    queue5.pop();
    assertEquals(8, queue5.peek());
  }
}

/*
Adding to tail and removing from head mimics a Queue
We need to track the tail for O(1) insertion
 */
class LinkedListQueue {

  private Node head;
  private Node tail;
  private int size;

  public LinkedListQueue() {
  }

  public void push(int x) {
    if (size == 0) { // Empty to 1
      head = tail = new Node(x);
    } else {
      tail.next = new Node(x);
      tail = tail.next;
    }
    size++;
  }

  public int pop() {
    if (size == 0) {
      return -1;
    }
    int e = head.data;
    if (size == 1) { // 1 to empty
      head = tail = null;
    } else {
      head = head.next;
    }
    size--;
    return e;
  }

  public int peek() {
    if (size == 0) {
      return -1;
    }
    return head.data;
  }

  public boolean isEmpty() {
    return size == 0;
  }
}

class Node {

  final int data;
  Node next;

  public Node(int data) {
    this.data = data;
  }

  public Node(int data, Node next) {
    this.data = data;
    this.next = next;
  }
}
