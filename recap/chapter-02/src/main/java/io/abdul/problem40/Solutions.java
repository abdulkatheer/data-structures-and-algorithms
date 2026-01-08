package io.abdul.problem40;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

// https://leetcode.com/problems/water-and-jug-problem/
// tag:math tag:graph tag:depth_first_search tag:breadth_first_search
public class Solutions {

}

/*
Better
T - O(x * y)
S - O(x * y)

State Space Search
Graph - DFS
6 possible transitions from every state
*/
class Solution {

  public boolean canMeasureWater(int x, int y, int target) {
    if (x + y < target) {
      return false; // impossible
    }

    Stack<int[]> stack = new Stack<>();
    boolean[][] visited = new boolean[x + 1][y + 1];
    stack.push(new int[]{0, 0});
    visited[0][0] = true;

    while (!stack.isEmpty()) {
      int[] state = stack.pop();

      int a = state[0];
      int b = state[1];

      if (a + b == target) {
        return true;
      }

      // Fill x & y
      visit(x, b, visited, stack);
      visit(a, y, visited, stack);

      // Empty x & y
      visit(0, b, visited, stack);
      visit(a, 0, visited, stack);

      // Pour x <-> y
      int needA = x - a;
      if (b > needA) {
        visit(x, b - needA, visited, stack); // a is full
      } else {
        visit(a + b, 0, visited, stack); // b is empty
      }

      int needB = y - b;
      if (a > needB) {
        visit(a - needB, y, visited, stack); // b is full
      } else {
        visit(0, a + b, visited, stack); // a is empty
      }
    }

    return false;
  }

  private void visit(int x, int y, boolean[][] visited, Stack<int[]> stack) {
    if (!visited[x][y]) {
      visited[x][y] = true;
      stack.push(new int[]{x, y});
    }
  }
}

/*
Better
T - O(x * y)
S - O(x * y)

State Space Search
Graph - BFS
6 possible transitions from every state
*/
class Solution2 {
  public boolean canMeasureWater(int x, int y, int target) {
    if (x + y < target) {
      return false; // impossible
    }

    Queue<int[]> q = new LinkedList<>();
    boolean[][] visited = new boolean[x+1][y+1];

    q.add(new int[] {0,0});
    visited[0][0] = true;

    while(!q.isEmpty()) {
      int size = q.size();
      for (int i = 0; i < size; i++) {
        int[] state = q.poll();
        int a = state[0];
        int b = state[1];

        if (a + b == target) {
          return true;
        }

        // Fill x and y
        visit(x, b, visited, q);
        visit(a, y, visited, q);

        // Empty x and y
        visit(0, b, visited, q);
        visit(a, 0, visited, q);

        // Pour x <-> y
        int needA = x - a;
        if (b > needA) {
          visit(x, b - needA, visited, q); // a is full
        } else {
          visit(a + b, 0, visited, q); // b is empty
        }

        int needB = y - b;
        if (a > needB) {
          visit(a - needB, y, visited, q); // b is full
        } else {
          visit(0, a + b, visited, q); // a is empty
        }
      }
    }

    return false;
  }

  private void visit(int x, int y, boolean[][] visited, Queue<int[]> queue) {
    if (!visited[x][y]) {
      visited[x][y] = true;
      queue.add(new int[] { x, y });
    }
  }
}

/*
Optimal - Bezout's Identity
T - O(log(min(x,y))
S - O(1)

the operations are actually add and subtract between each other
given two nums x and y, by adding and subtracting each other, the smallest unit we can get is gcd(x,y)
and we can get only in multiples of gcd
*/
class Solution3 {
  public boolean canMeasureWater(int x, int y, int target) {
    return target <= x + y  && target % gcd(x, y) == 0;
  }

  private int gcd(int a, int b) {
    while (b != 0) {
      int temp = b;
      b = a % b;
      a = temp;
    }

    return a;
  }
}
