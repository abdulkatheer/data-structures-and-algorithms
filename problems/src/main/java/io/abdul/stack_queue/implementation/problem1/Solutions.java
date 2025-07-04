package io.abdul.stack_queue.implementation.problem1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Solutions {

  public static void main(String[] args) {
    // Example 1
    ArrayStack stack1 = new ArrayStack();
    stack1.push(5);
    stack1.push(10);
    assertEquals(10, stack1.top());
    assertEquals(10, stack1.pop());
    assertFalse(stack1.isEmpty());

    // Example 2
    ArrayStack stack2 = new ArrayStack();
    assertTrue(stack2.isEmpty());
    stack2.push(1);
    assertEquals(1, stack2.pop());
    assertTrue(stack2.isEmpty());

    // Example 3
    ArrayStack stack3 = new ArrayStack();
    assertTrue(stack3.isEmpty());

    // Custom case: multiple pushes and pops
    ArrayStack stack4 = new ArrayStack();
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
    ArrayStack stack5 = new ArrayStack();
    stack5.push(7);
    assertFalse(stack5.isEmpty());
    assertEquals(7, stack5.top());
    stack5.push(8);
    assertEquals(8, stack5.top());
    stack5.pop();
    assertEquals(7, stack5.top());
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
}

