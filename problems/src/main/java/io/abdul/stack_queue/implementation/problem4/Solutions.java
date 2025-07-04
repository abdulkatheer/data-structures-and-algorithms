package io.abdul.stack_queue.implementation.problem4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
Moving elements from Stack to another Stack will reverse the element order
Moving that again will bring the order back.
 */
public class Solutions {

  public static void main(String[] args) {
    // Example 1
    StackQueue queue1 = new StackQueue();
    queue1.push(5);
    queue1.push(10);
    assertEquals(5, queue1.peek());
    assertEquals(5, queue1.pop());
    assertFalse(queue1.isEmpty());

    // Example 2
    StackQueue queue2 = new StackQueue();
    assertTrue(queue2.isEmpty());

    // Example 3
    StackQueue queue3 = new StackQueue();
    queue3.push(1);
    assertEquals(1, queue3.pop());
    assertTrue(queue3.isEmpty());

    // Custom case: multiple pushes and pops
    StackQueue queue4 = new StackQueue();
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
    StackQueue queue5 = new StackQueue();
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

Unline QueueStack, where we could take elements from front added to back.
Here we can only take and add to front.
So need a temp to keep elements.
 */
class StackQueue {

  private final ArrayStack stack = new ArrayStack();
  private final ArrayStack temp = new ArrayStack();

  public StackQueue() {
  }

  /*
  Empty the stack and put it in temp
  Add element
  Empty temp and put it in stack

  Add 1
  stack [1]
  temp []

  Add 2
  stack []
  temp [1]
  stack [2]
  temp [1]
  stack [1, 2]
  temp []

  Add 3
  stack [1, 2]
  temp []
  stack []
  temp [2, 1]
  stack [3]
  temp [2, 1]
  stack [1, 2, 3]

   */
  public void push(int x) {
    if (stack.isFull()) {
      return;
    }

    while (!stack.isEmpty()) {
      temp.push(stack.pop());
    }

    stack.push(x);

    while (!temp.isEmpty()) {
      stack.push(temp.pop());
    }
  }

  public int pop() {
    return stack.pop();
  }

  public int peek() {
    return stack.top();
  }

  public boolean isEmpty() {
    return stack.isEmpty();
  }
}

/*
We have two stacks. One maintaining elements in the LIFO order, another maintains in FIFO order.
How? when we copy from one stack to another it's reversed.

When do we copy from stack1 to stack2?
Only when stack2 is empty. Otherwise newly added elements will be on top of old elements.

Add 1,2,3
stack1 [3,2,1]
stack2 []

top
stack1 []
stack2 [1,2,3]
returns 1

Add 4
stack1 [4]
stack2 [1,2,3]

if we copy now,
stack1 []
stack2 [4,1,2,3] - this is wrong

So copy only when stack2 is empty
 */
class StackQueue2 {

  private final ArrayStack stack1 = new ArrayStack();
  private final ArrayStack stack2 = new ArrayStack();

  public StackQueue2() {
  }

  public void push(int x) {
    if (stack1.isFull()) {
      return;
    }

    stack1.push(x);
  }

  public int pop() {
    if (stack1.isEmpty() && stack2.isEmpty()) { // Empty queue
      return -1;
    }

    if (!stack2.isEmpty()) { // lifo exists, we can pop it
      return stack2.pop();
    }

    while (!stack1.isEmpty()) { // lifo is drained fully, we can refill now, lifo to fifo order
      stack2.push(stack1.pop());
    }

    return stack2.pop();
  }

  public int peek() {
    if (stack1.isEmpty() && stack2.isEmpty()) { // Empty queue
      return -1;
    }

    if (!stack2.isEmpty()) { // lifo exists, we can top it
      return stack2.top();
    }

    while (!stack1.isEmpty()) { // lifo is drained fully, we can refill now, lifo to fifo order
      stack2.push(stack1.pop());
    }

    return stack2.top();
  }

  public boolean isEmpty() {
    return stack1.isEmpty() && stack2.isEmpty();
  }
}

class ArrayStack {

  private final int[] stack = new int[100];

  private int top = -1;

  public ArrayStack() {
  }

  public void push(int x) {
    if (top == stack.length - 1) {
      return;
    }
    stack[++top] = x;
  }

  public int pop() {
    if (top == -1) {
      return -1;
    }
    return stack[top--];
  }

  public int top() {
    if (top == -1) {
      return -1;
    }
    return stack[top];
  }

  public boolean isEmpty() {
    return top == -1;
  }

  int getSize() {
    return top + 1;
  }

  boolean isFull() {
    return top + 1 == stack.length;
  }
}

