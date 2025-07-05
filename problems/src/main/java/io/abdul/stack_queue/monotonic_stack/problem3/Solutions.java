package io.abdul.stack_queue.monotonic_stack.problem3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/*
1 2 3 -3 -2 -1

10 2 -1 6 -6 7 -20 -1 23 6 -1
10 2 -1 6 -6 7 -20 -1 23 6 0
10 2 -1 6 -6 0 -20 -1 23 6 0
10 2 -1 0 0 0 -20 -1 23 6 0
10 2 0 0 0 0 -20 -1 23 6 0
 */
public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();

    // Basic examples
    assertArrayEquals(new int[]{}, solution.asteroidCollision(new int[]{2, -2}));
    assertArrayEquals(new int[]{10, 20}, solution.asteroidCollision(new int[]{10, 20, -10}));
    assertArrayEquals(new int[]{10}, solution.asteroidCollision(new int[]{10, 2, -5}));

    // No collisions: all moving right
    assertArrayEquals(new int[]{1, 2, 3}, solution.asteroidCollision(new int[]{1, 2, 3}));

    // No collisions: all moving left
    assertArrayEquals(new int[]{-3, -2, -1}, solution.asteroidCollision(new int[]{-3, -2, -1}));

    // Multiple chain collisions
    assertArrayEquals(new int[]{5}, solution.asteroidCollision(new int[]{5, 10, -5, -10, -2}));
    assertArrayEquals(new int[]{}, solution.asteroidCollision(new int[]{8, -8}));

    // Large asteroid survives
    assertArrayEquals(new int[]{50}, solution.asteroidCollision(new int[]{1, -1, 2, -2, 50}));

    // One asteroid survives at the end
    assertArrayEquals(new int[]{-5, -10, 100}, solution.asteroidCollision(new int[]{-5, -10, 100}));

    // Alternating with destruction
    assertArrayEquals(new int[]{1}, solution.asteroidCollision(new int[]{1, 2, 3, -3, -2}));

    // Edge values
    assertArrayEquals(new int[]{1_000_000},
        solution.asteroidCollision(new int[]{1_000_000, -999_999}));
    assertArrayEquals(new int[]{-1_000_000},
        solution.asteroidCollision(new int[]{999_999, -1_000_000}));

    // Stress test: all right then one huge left
    int[] large = new int[100000];
    Arrays.fill(large, 1);
    large[99999] = -1000000;
    int[] expected = new int[]{-1000000};
    assertArrayEquals(expected, solution.asteroidCollision(large));
  }
}

/*
Brute-force

T - O(n^2)
S - O(n)

Traverse from right to left, for each positive, find next occurring first negative, as they'll meet them in any of their next moves.
If both are same, both are zero
Otherwise bigger one exists
 */
class Solution {

  public int[] asteroidCollision(int[] asteroids) {
    int n = asteroids.length;
    int[] ast = new int[n];
    System.arraycopy(asteroids, 0, ast, 0, n);

    for (int i = n - 1; i >= 0; i--) {
      if (ast[i] < 0) { // we need only positives
        continue;
      }
      for (int j = i + 1; j < n; j++) {
        if (ast[j] == 0) {
          continue;
        }
        if (ast[j] < 0) {
          int res = ast[i] + ast[j];
          if (res == 0) { // same value
            ast[i] = ast[j] = 0;
            break; // break after i is fully clashed
          } else if (res > 0) { // i is bigger
            ast[j] = 0;
          } else { // j is bigger
            ast[i] = 0;
            break; // break after i is fully clashed
          }
        }
      }
    }

    ArrayList<Integer> result = new ArrayList<>();
    for (int i : ast) {
      if (i != 0) {
        result.add(i);
      }
    }

    return result.stream().mapToInt(value -> value).toArray();
  }
}

/*
Optimal
Modified monotonic stack

T - O(n) - 2n
S - O(n)

10 2 -1 6 -6 7 -20 -1 23 6 -1

s [-1]
s [6]
s [23 6]
s [-1 23 6]
s [-20 -1 23 6]
s [-20 -1 23 6]
s [-6 -20 -1 23 6]
s [-20 -1 23 6]
s [-1 -20 -1 23 6]
s [2 -20 -1 23 6]
s [10 2 -20 -1 23 6]
 */
class Solution2 {

  public int[] asteroidCollision(int[] asteroids) {
    int n = asteroids.length;

    Stack<Integer> stack = new Stack<>();
    for (int i = n - 1; i >= 0; i--) {
      if (asteroids[i] < 0) { // negative just goes into the stack
        stack.push(asteroids[i]);
        continue;
      }

      boolean insert = true;
      while (!stack.isEmpty() && stack.peek() < 0) {
        int res = asteroids[i] + stack.peek();
        if (res > 0) { // i is bigger
          // smaller positive found, j has to be dropped
          stack.pop();
        } else if (res < 0) { // j is bigger
          // bigger positive found, i has to be dropped
          insert = false;
          break;
        } else { // both are equals
          // both has to be dropped
          stack.pop();
          insert = false;
          break;
        }
      }

      if (insert) {
        stack.push(asteroids[i]);
      }
    }

    int[] result = new int[stack.size()];
    int i = 0;
    while (!stack.isEmpty()) {
      result[i] = stack.pop();
      i++;
    }

    return result;
  }
}