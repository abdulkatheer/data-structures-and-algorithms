package io.abdul.strings_advanced_algo.medium_problems.problem2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
//    Solution2 sol = new Solution2();
//    Solution3 sol = new Solution3();
//    Solution4 sol = new Solution4();
//    Solution5 sol = new Solution5();
//    Solution6 sol = new Solution6();
    Solution7 sol = new Solution7();

    // Edge cases
    assertEquals(0, sol.countRev(""), "Empty string should need 0 reversals");
    assertEquals(-1, sol.countRev("("), "Odd length strings are impossible");

    // Already balanced
    assertEquals(0, sol.countRev("()"), "Already balanced");
    assertEquals(0, sol.countRev("((())())"), "Already balanced (nested)");

    // Simple reversals
    assertEquals(1, sol.countRev("(("), "Single reversal needed");
    assertEquals(2, sol.countRev("))(("), "Two reversals needed");

    // Provided examples
    assertEquals(3, sol.countRev(")(())((("), "Given example 1");
    assertEquals(-1, sol.countRev("(()((()(())(("), "Given example 2");

    // All '(' or all ')'
    assertEquals(2, sol.countRev("(((("), "All '(' needs 2 flips");
    assertEquals(2, sol.countRev("))))"), "All ')' needs 2 flips");

    // Mixed possible
    assertEquals(2, sol.countRev("())("), "Mixed possible");
    assertEquals(2, sol.countRev("(()))("), "Mixed possible case 2");

    // Complex but fixable
    assertEquals(-1, sol.countRev("))(())("), "Complex case fixable");

    // Large alternating
    assertEquals(0, sol.countRev("()()()"), "Large already balanced");
    assertEquals(2, sol.countRev(")()()("), "Large pattern fixable");
  }
}

// Brute-force
// T - O(n * 2^n)
class Solution {

  public int countRev(String s) {
    return minReversals(s);
  }

  private int minReversals(String s) {
    List<Character> stack = new ArrayList<>(s.length());

    return minReversals(0, s, stack);
  }

  // -1 means invalid
  private int minReversals(int i, String s, List<Character> stack) {
    if (i == s.length()) {
      return isBalanced(stack) ? 0 : -1;
    }

    int asIs;
    int rev;
    if (s.charAt(i) == '(') {
      stack.add('(');
      asIs = minReversals(i + 1, s, stack);
      stack.remove(stack.size() - 1);

      stack.add(')');
      rev = minReversals(i + 1, s, stack);
      stack.remove(stack.size() - 1);
      if (rev != -1) {
        rev = rev + 1;
      }
    } else {
      stack.add(')');
      asIs = minReversals(i + 1, s, stack);
      stack.remove(stack.size() - 1);

      stack.add('(');
      rev = minReversals(i + 1, s, stack);
      stack.remove(stack.size() - 1);
      if (rev != -1) {
        rev = rev + 1;
      }
    }

    if (asIs == -1) {
      return rev;
    } else if (rev == -1) {
      return asIs;
    } else {
      return Math.min(asIs, rev);
    }
  }

  private boolean isBalanced(List<Character> chars) {
    int count = 0;

    for (char c : chars) {
      if (c == '(') {
        count++;
      } else if (c == ')') {
        if (count == 0) {
          return false; // a closing came before an opening
        }
        count--;
      }
    }

    return count == 0; // all opens matched
  }
}

/*
Instead of checking the whole string is balanced or not, we maintain two counts.
unmatched '(' and unmatched ')'

In general, we're trying to balance out the expression at every step of recursion.
At the end, whatever is unmatched, we'll flip to make it matched.

For '(', we can only increase unmatched '(', it can't cancel out ')'
For ')', we can cancel unmatched '('. If no unmatched '(' exists, means the expression is empty.
When expression is empty, and we add ')' to it, we're invalidating the expression.
So we stop the route early, because no future occurrences of '(' or ')' reduce the close count.

 */
class Solution2 {

  public int countRev(String s) {
    if (s.length() % 2 != 0) {
      return -1; // impossible
    }
    return solve(0, 0, 0, s);
  }

  // i = index
  // open = unmatched '('
  // close = unmatched ')'
  private int solve(int i, int open, int close, String s) {
    // Invalid state pruning
    if (close > 0) {
      return -1; // can't fix unmatched ')'
    }

    // End of string
    if (i == s.length()) {
      // All unmatched '(' must be paired by flipping
      // close must already be 0 to reach here
      if (open % 2 != 0) {
        return -1;
      }
      return open / 2;
    }

    char c = s.charAt(i);

    // ---------------------------
    // Option 1: Keep as-is
    // ---------------------------
    int keepAns;
    if (c == '(') {
      keepAns = solve(i + 1, open + 1, close, s);
    } else { // ')'
      // Can cancel out an existing unmatched '(' OR make the expression invalid by adding ')'
      if (open > 0) {
        keepAns = solve(i + 1, open - 1, close, s);
      } else {
        keepAns = solve(i + 1, open, close + 1, s); // unmatched ')'
      }
    }

    // ---------------------------
    // Option 2: Flip the character
    // ---------------------------
    int flipAns;
    if (c == '(') {
      // flip to ')'
      // ')' can cancel out an existing unmatched '(' OR make the expression invalid by adding ')'
      if (open > 0) {
        flipAns = solve(i + 1, open - 1, close, s);
      } else {
        flipAns = solve(i + 1, open, close + 1, s); // unmatched ')'
      }
    } else { // c == ')'
      // flip to '('
      flipAns = solve(i + 1, open + 1, close, s);
    }

    if (flipAns != -1) {
      flipAns += 1; // cost of flipping
    }

    // ---------------------------
    // Choose minimum valid
    // ---------------------------
    if (keepAns == -1) {
      return flipAns;
    }
    if (flipAns == -1) {
      return keepAns;
    }
    return Math.min(keepAns, flipAns);
  }
}

/*
Technically, we don't need close at all.
We're trying to keep the expression balanced at every step.
If it can't be balanced with any future character, we stop exploring that route.

((((, can be balanced in future with 4 ')'
), can't be balanced in future at all.

So open count is the only one we need.
 */
class Solution3 {

  public int countRev(String s) {
    if (s.length() % 2 != 0) { // not even
      return -1; // impossible
    }
    return solve(0, 0, s);
  }

  // i = index
  // open = unmatched '('
  private int solve(int i, int open, String s) {

    // End of string
    if (i == s.length()) {
      // All unmatched '(' must be paired by flipping
      if (open % 2 != 0) { // odd open can't be flipped to balance
        return -1;
      }
      return open / 2;
    }

    char c = s.charAt(i);

    // ---------------------------
    // Option 1: Keep as-is
    // ---------------------------
    int keepAns;
    if (c == '(') {
      // increase unmatched '(', may get balanced in future
      keepAns = solve(i + 1, open + 1, s);
    } else { // ')'
      if (open > 0) {
        // Can cancel out an existing unmatched '('
        keepAns = solve(i + 1, open - 1, s);
      } else {
        // make the expression invalid by adding ')'
        keepAns = -1; // unmatched ')'
      }
    }

    // ---------------------------
    // Option 2: Flip the character
    // ---------------------------
    int flipAns;
    if (c == '(') {
      // flip to ')'
      if (open > 0) {
        // ')' can cancel out an existing unmatched '('
        flipAns = solve(i + 1, open - 1, s);
      } else {
        // make the expression invalid by adding ')'
        flipAns = -1; // unmatched ')'
      }
    } else { // c == ')'
      // flip to '('
      // increase unmatched '(', may get balanced in future
      flipAns = solve(i + 1, open + 1, s);
    }

    if (flipAns != -1) {
      flipAns += 1; // cost of flipping
    }

    // ---------------------------
    // Choose minimum valid
    // ---------------------------
    if (keepAns == -1) {
      return flipAns;
    }
    if (flipAns == -1) {
      return keepAns;
    }
    return Math.min(keepAns, flipAns);
  }
}

// Solution3 + Memoization
class Solution4 {

  public int countRev(String s) {
    if (s.length() % 2 != 0) { // not even
      return -1; // impossible
    }
    int[][] dp = new int[s.length()][s.length() + 1]; // i,open
    for (int[] ints : dp) {
      Arrays.fill(ints, -1);
    }
    return solve(0, 0, s, dp);
  }

  // i = index
  // open = unmatched '('
  private int solve(int i, int open, String s, int[][] dp) {

    // End of string
    if (i == s.length()) {
      // All unmatched '(' must be paired by flipping
      if (open % 2 != 0) { // odd open can't be flipped to balance
        return -1;
      }
      return open / 2;
    }

    if (dp[i][open] != -1) {
      return dp[i][open];
    }

    char c = s.charAt(i);

    // ---------------------------
    // Option 1: Keep as-is
    // ---------------------------
    int keepAns;
    if (c == '(') {
      // increase unmatched '(', may get balanced in future
      keepAns = solve(i + 1, open + 1, s, dp);
    } else { // ')'
      if (open > 0) {
        // Can cancel out an existing unmatched '('
        keepAns = solve(i + 1, open - 1, s, dp);
      } else {
        // make the expression invalid by adding ')'
        keepAns = -1; // unmatched ')'
      }
    }

    // ---------------------------
    // Option 2: Flip the character
    // ---------------------------
    int flipAns;
    if (c == '(') {
      // flip to ')'
      if (open > 0) {
        // ')' can cancel out an existing unmatched '('
        flipAns = solve(i + 1, open - 1, s, dp);
      } else {
        // make the expression invalid by adding ')'
        flipAns = -1; // unmatched ')'
      }
    } else { // c == ')'
      // flip to '('
      // increase unmatched '(', may get balanced in future
      flipAns = solve(i + 1, open + 1, s, dp);
    }

    if (flipAns != -1) {
      flipAns += 1; // cost of flipping
    }

    // ---------------------------
    // Choose minimum valid
    // ---------------------------
    if (keepAns == -1) {
      return flipAns;
    }
    if (flipAns == -1) {
      return keepAns;
    }
    int min = Math.min(keepAns, flipAns);
    dp[i][open] = min;
    return min;
  }
}

// Stack Overflow
// Saves time, but not the recursion stack
class Solution5 {

  public int countRev(String s) {
    if (s.length() % 2 != 0) { // not even
      return -1; // impossible
    }
    int[][] dp = new int[s.length()][s.length() + 1]; // i,open
    for (int[] ints : dp) {
      Arrays.fill(ints, -1);
    }
    return solve(0, 0, s, dp);
  }

  // i = index
  // open = unmatched '('
  private int solve(int i, int open, String s, int[][] dp) {

    // End of string
    if (i == s.length()) {
      // All unmatched '(' must be paired by flipping
      if (open % 2 != 0) { // odd open can't be flipped to balance
        return -1;
      }
      return open / 2;
    }

    if (dp[i][open] != -1) {
      return dp[i][open];
    }

    char c = s.charAt(i);

    // ---------------------------
    // Option 1: Keep as-is
    // ---------------------------
    int keepAns;
    if (c == '(') {
      // increase unmatched '(', may get balanced in future
      keepAns = solve(i + 1, open + 1, s, dp);
    } else { // ')'
      if (open > 0) {
        // Can cancel out an existing unmatched '('
        keepAns = solve(i + 1, open - 1, s, dp);
      } else {
        // make the expression invalid by adding ')'
        keepAns = -1; // unmatched ')'
      }
    }

    // ---------------------------
    // Option 2: Flip the character
    // ---------------------------
    int flipAns;
    if (c == '(') {
      // flip to ')'
      if (open > 0) {
        // ')' can cancel out an existing unmatched '('
        flipAns = solve(i + 1, open - 1, s, dp);
      } else {
        // make the expression invalid by adding ')'
        flipAns = -1; // unmatched ')'
      }
    } else { // c == ')'
      // flip to '('
      // increase unmatched '(', may get balanced in future
      flipAns = solve(i + 1, open + 1, s, dp);
    }

    if (flipAns != -1) {
      flipAns += 1; // cost of flipping
    }

    // ---------------------------
    // Choose minimum valid
    // ---------------------------
    if (keepAns == -1) {
      return flipAns;
    }
    if (flipAns == -1) {
      return keepAns;
    }
    int min = Math.min(keepAns, flipAns);
    dp[i][open] = min;
    return min;
  }
}

/*
Better - Greedy
T - O(n)
S - O(n) stack

Greedy algorithms work by making locally optimal choices at each step, hoping they lead to a globally optimal solution.

Local greedy decision:
1) Every time we see a ')', we immediately match it with an unmatched '(' if one exists.
2) Otherwise, we treat it as unmatched ')' (and will need a flip later).
We never backtrack — we always take the “best immediate option” for balancing.

Instead of exploring all possibilities, we just count number of unbalanced '(' and ')'.
Everything else is self-balancing.
Ex: )(())(((
) ... ((( are unbalanced
How many flips?
(open+1)/2 + (close+1)/2 = 1 + 2 = 3
How this formular arrived?
Each pair of same brackets → 1 flip
ex: (((( -> 2 flips
Left out requires 1 flip
ex: ((((( ->  3 flips

That's why we just +1 and /2 which gives this number.

Linear recursive solution too has Stack Overflow, it's just the same as above
Saves time, but not the stack depth
 */
class Solution6 {
  public int countRev(String s) {
    if (s.length() % 2 != 0) return -1; // impossible
    return solve(s, 0, 0, 0);
  }

  // i = index, open = unmatched '(', close = unmatched ')'
  private int solve(String s, int i, int open, int close) {
    if (i == s.length()) {
      return (open + 1) / 2 + (close + 1) / 2;
    }

    char c = s.charAt(i);
    if (c == '(') {
      return solve(s, i + 1, open + 1, close);
    } else { // ')'
      if (open > 0) return solve(s, i + 1, open - 1, close);
      else return solve(s, i + 1, open, close + 1);
    }
  }
}

// Greedy Iterative
class Solution7 {
  public int countRev(String s) {
    int n = s.length();
    if (n % 2 != 0) return -1; // impossible

    int open = 0;
    int close = 0;

    for (int i = 0; i < n; i++) {
      char c = s.charAt(i);

      // Greedily balancing the expression and finding unbalanced count
      if (c == '(') {
        open++;
      } else { // ')'
        if (open > 0) {
          open--;
        } else {
          close++;
        }
      }
    }

    return (open + 1) / 2 + (close + 1) / 2;
  }
}