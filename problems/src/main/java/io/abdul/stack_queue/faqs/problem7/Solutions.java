package io.abdul.stack_queue.faqs.problem7;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();
//    Solution2 solution = new Solution2();

    // Test case 1: Celebrity is person 1
    int[][] M1 = {
        {0, 1, 1, 0},
        {0, 0, 0, 0},
        {1, 1, 0, 0},
        {0, 1, 1, 0}
    };
    assertEquals(1, solution.celebrity(M1));

    // Test case 2: No celebrity
    int[][] M2 = {
        {0, 1},
        {1, 0}
    };
    assertEquals(-1, solution.celebrity(M2));

    // Test case 3: Celebrity is person 1
    int[][] M3 = {
        {0, 1, 0},
        {0, 0, 0},
        {0, 1, 0}
    };
    assertEquals(1, solution.celebrity(M3));

    // Edge case: Only 1 person (is trivially a celebrity)
    int[][] M4 = {
        {0}
    };
    assertEquals(0, solution.celebrity(M4));

    // Edge case: Everyone knows someone (no celebrity)
    int[][] M5 = {
        {0, 1, 1},
        {1, 0, 1},
        {1, 1, 0}
    };
    assertEquals(-1, solution.celebrity(M5));

    // Edge case: Person 0 is celebrity
    int[][] M6 = {
        {0, 0, 0, 0},
        {1, 0, 1, 1},
        {1, 0, 0, 1},
        {1, 0, 1, 0}
    };
    assertEquals(0, solution.celebrity(M6));
  }
}

/*
Brute-force

T - O(n^2)
S - O(n)

Iterate all and count knows and knownBy.
Result should be the one whose knows is 0 and knownBy is n
 */
class Solution {

  public int celebrity(int[][] M) {
    int n = M.length;
    int[] knows = new int[n];
    int[] knownBy = new int[n];

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (M[i][j] == 1) {
          knows[i]++; // i knows j
          knownBy[j]++; // j knownBy i
        }
      }
    }

    for (int i = 0; i < n; i++) {
      if (knows[i] == 0 && knownBy[i] == n - 1) {
        return i;
      }
    }

    return -1;
  }
}

/*
Optimal - Eliminating possibilities
[TODO similar to an earlier matrix type problem, find it]

T - O(n)
S - O(1)

 */
class Solution2 {

  public int celebrity(int[][] M) {
    int left = 0, right = M.length - 1;

    if (M.length == 1) { // only 1 and a trivial celebrity
      return 0;
    }

    int candidate = -1;
    while (left < right) {
      if (M[left][right] == 1) { // left eliminated as it knows right
        candidate = right;
        left++;
      } else { // right eliminated as it's not known by left
        candidate = left;
        right--;
      }
    }

    // we left with one candidate now
    boolean celebrity = true;
    for (int i = 0; i < M.length; i++) {
      if (i == candidate) { // skip candidate
        continue;
      }
      if (M[i][candidate] == 0) { // i doesn't know our celebrity candidate, so can't be a celebrity
        celebrity = false;
        break;
      }
      if (M[candidate][i] == 1) { // our celebrity candidate knows i, so can't be a celebrity
        celebrity = false;
        break;
      }
    }
    return celebrity ? candidate : -1;
  }
}