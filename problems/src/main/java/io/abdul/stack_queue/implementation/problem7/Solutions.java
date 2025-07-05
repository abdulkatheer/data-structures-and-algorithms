package io.abdul.stack_queue.implementation.problem7;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
    Solution sol = new Solution();

    // Valid balanced cases
    assertTrue(sol.isValid("()[{}()]"));
    assertTrue(sol.isValid("{[()]}"));
    assertTrue(sol.isValid("()"));
    assertTrue(sol.isValid("({[]})"));

    // Invalid cases
    assertFalse(sol.isValid("[()"));
    assertFalse(sol.isValid("[(])"));
    assertFalse(sol.isValid("({[)]}"));
    assertFalse(sol.isValid("(()"));
    assertFalse(sol.isValid("())"));
    assertFalse(sol.isValid("{"));

    // Edge cases
    assertTrue(sol.isValid("")); // empty string is considered balanced
    assertFalse(sol.isValid("]"));
    assertFalse(sol.isValid("["));
    assertFalse(sol.isValid("([)"));

    // Long balanced input
    assertTrue(sol.isValid("({[]})".repeat(1000)));

    // Long unbalanced input
    assertFalse(sol.isValid("({[]})".repeat(999) + "("));
  }
}

/*
1) opening and closing should be balanced
2) order matters
{([])} -> right
{([)]} -> wrong
the close should match the last opened -> to get last inserted, we need LIFO
 */
class Solution {

  public boolean isValid(String str) {
    Stack<Character> stack = new Stack<>();

    boolean inOrder = true;
    for (int i = 0; i < str.length(); i++) {
      char theChar = str.charAt(i);
      if (theChar == '(' || theChar == '{' || theChar == '[') {
        stack.push(theChar);
      } else {
        if (stack.isEmpty()) {
          inOrder = false;
          break;
        } else if ((stack.peek() == '(' && theChar == ')') || (stack.peek() == '{'
            && theChar == '}') || (
            stack.peek() == '[' && theChar == ']')) {
          stack.pop();
        } else {
          inOrder = false;
          break;
        }
      }
    }

    // In order and all open and close are balanced
    return inOrder && stack.isEmpty();
  }
}