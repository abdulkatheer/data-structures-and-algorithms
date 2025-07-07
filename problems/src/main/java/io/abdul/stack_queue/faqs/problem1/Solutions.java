package io.abdul.stack_queue.faqs.problem1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
    MinStack minStack = new MinStack();

    minStack.push(-2);
    minStack.push(0);
    minStack.push(-3);
    assertEquals(-3, minStack.getMin()); // min is -3
    minStack.pop();
    assertEquals(0, minStack.top());     // top is 0
    assertEquals(-2, minStack.getMin()); // min is -2

    minStack = new MinStack();
    minStack.push(5);
    minStack.push(1);
    assertEquals(1, minStack.getMin());  // min is 1
    minStack.push(3);
    minStack.pop();
    assertEquals(1, minStack.getMin());  // min is still 1
    assertEquals(1, minStack.top());     // top is 1

    // Edge cases
    minStack = new MinStack();
    minStack.push((int) 1e5);
    assertEquals((int) 1e5, minStack.getMin());

    minStack.push(-(int) 1e5);
    assertEquals(-(int) 1e5, minStack.getMin());

    minStack.pop();
    assertEquals((int) 1e5, minStack.getMin());

    // Single element push-pop
    minStack = new MinStack();
    minStack.push(42);
    assertEquals(42, minStack.top());
    assertEquals(42, minStack.getMin());
    minStack.pop();
    // no further operation after this since stack is empty
  }
}

/*
Brute-force

T - O(1) for all ops
S - O(n) - 2n; n for the stack elements; n for the min

Keep min as well as actual top at each step!

 */
class MinStack {

  private final Stack<int[]> stack = new Stack<>();

  public MinStack() {
  }

  public void push(int val) {
    if (stack.isEmpty()) { // val is the new mins
      stack.push(new int[]{val, val});
    } else {
      int currentMin = stack.peek()[1];
      if (val < currentMin) { // new min
        stack.push(new int[]{val, val});
      } else { // currentMin sustains
        stack.push(new int[]{val, currentMin});
      }
    }
  }

  public void pop() {
    if (stack.isEmpty()) {
      return;
    }
    stack.pop();
  }

  public int top() {
    if (stack.isEmpty()) {
      return -1;
    }
    return stack.peek()[0];
  }

  public int getMin() {
    if (stack.isEmpty()) {
      return -1;
    }
    return stack.peek()[1];
  }
}

/*
Optimal - Math trick

T - O(1) for all ops
S - O(n) - for the stack

 */
class MinStack2 {

  private final Stack<Integer> stack = new Stack<>();
  private int min = (int) 1e5; // max as given in problem

  public MinStack2() {
  }

  public void push(int val) {
    if (stack.isEmpty()) {
      min = val;
      stack.push(val);
    } else {
      if (val < min) { // new min found!
        int magicValue = (2 * val) - min;
        min = val;
        stack.push(magicValue);
      } else {
        stack.push(val);
      }
    }
  }

  public void pop() {
    if (stack.isEmpty()) {
      return;
    }

    Integer top = stack.pop();
    if (top < min) { // our magic value was on the top, so need to rollback the min
      min = (2 * min) - top; // previousMin from magicValue and currentMin
    }
  }

  public int top() {
    if (stack.isEmpty()) {
      return -1;
    }

    Integer top = stack.peek();
    if (top < min) { // our magic value is on the top, so need to return the min
      return min;
    }

    return top; // original value, just return it
  }

  public int getMin() {
    return min;
  }
}