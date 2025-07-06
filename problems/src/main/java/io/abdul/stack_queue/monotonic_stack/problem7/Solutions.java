package io.abdul.stack_queue.monotonic_stack.problem7;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();

    // Basic examples
//    assertEquals("1892", solution.removeKdigits("541892", 2));
    assertEquals("21", solution.removeKdigits("1002991", 3));
    assertEquals("0", solution.removeKdigits("10", 2));

    // Remove all digits
    assertEquals("0", solution.removeKdigits("12345", 5));

    // Removing from already smallest number
    assertEquals("0", solution.removeKdigits("1000", 3));

    // Digits in increasing order
    assertEquals("123", solution.removeKdigits("123456", 3));

    // Digits in decreasing order
    assertEquals("1", solution.removeKdigits("7654321", 6));

    // All same digits
    assertEquals("1", solution.removeKdigits("11111", 4));
    assertEquals("0", solution.removeKdigits("11111", 5));

    // With internal zeros
    assertEquals("0", solution.removeKdigits("32000", 2));

    // Output must not contain leading zeros
    assertEquals("1219", solution.removeKdigits("1432219", 3));
    assertEquals("200", solution.removeKdigits("10200", 1));
    assertEquals("0", solution.removeKdigits("10200", 5));

    // k = 0 (no removal)
    assertEquals("456", solution.removeKdigits("456", 0));

    // Large input, performance check
    StringBuilder sb = new StringBuilder("9999999999");
    for (int i = 0; i < 1000; i++) {
      sb.append("9");
    }
    assertDoesNotThrow(() -> solution.removeKdigits(sb.toString(), 1000));
  }
}

/*
Brute-force - All combinations, recursive solution

T - O(2^n)
S - O(n) - stack

In how many ways we can pick n-k elements out of n elements
 */
class Solution {

  public String removeKdigits(String nums, int k) {
    if (nums.length() == k) {
      return "0";
    }

    StringBuilder result = new StringBuilder();
    smallestCombination(nums, 0, nums.length() - k, new StringBuilder(), result);
    trimLeadingZeroes(result);
    return result.isEmpty() ? "0" : result.toString();
  }

  private void smallestCombination(String nums, int i, int k, StringBuilder temp,
      StringBuilder result) {
    if (temp.length() == k) { // k elements are picked
      if (result.isEmpty()) {
        result.append(temp);
      } else {
        int compare = compare(temp, result);
        if (compare < 0) {
          result.delete(0, result.length());
          result.append(temp);
        }
      }
      return;
    }

    if (i == nums.length()) {
      return;
    }

    temp.append(nums.charAt(i));
    smallestCombination(nums, i + 1, k, temp, result);
    temp.deleteCharAt(temp.length() - 1);
    smallestCombination(nums, i + 1, k, temp, result);
  }

  private int compare(StringBuilder str1, StringBuilder str2) {
    str1 = new StringBuilder(str1);
    str2 = new StringBuilder(str2);
    trimLeadingZeroes(str1);
    trimLeadingZeroes(str2);
    if (str1.length() != str2.length()) {
      return Integer.compare(str1.length(), str2.length());
    }

    return str1.compareTo(str2); // length same, so lexicographical check
  }

  private void trimLeadingZeroes(StringBuilder str) {
    while (!str.isEmpty() && str.charAt(0) == '0') {
      str.deleteCharAt(0);
    }
  }
}

/*
Optimal - Math & Monotonic stack

Intuition - Remove the first k bigger elements, that'll definitely give the smallest num after removing k elements
 */
class Solution2 {

  public String removeKdigits(String nums, int k) {
    int n = nums.length();
    if (n == k) {
      return "0";
    }

    Stack<Character> stack = new Stack<>();

    for (int i = 0; i < n; i++) {
      while (k > 0 && !stack.isEmpty() && stack.peek() > nums.charAt(i)) {
        stack.pop();
        k--;
      }
      stack.push(nums.charAt(i));
    }

    while (k > 0) {
      // we can still remove a few chars, we could not find k bigger numbers in he front
      stack.pop();
      k--;
    }

    StringBuilder result = new StringBuilder();
    while (!stack.isEmpty()) {
      result.append(stack.pop());
    }

    while (!result.isEmpty() && result.charAt(result.length() - 1) == '0') {
      result.deleteCharAt(result.length() - 1); // remove leading zeroes
    }

    return result.isEmpty() ? "0" : result.reverse().toString();
  }
}