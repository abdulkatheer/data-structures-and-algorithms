package io.abdul.greedy_algorithms.hard.problem1;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidParenthesesChecker {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution4 solution = new Solution4();

        // Test Case 1: General case with valid parentheses
        String s1 = "(*))";
        assertTrue(solution.isValid(s1), "Test Case 1 Failed");

        // Test Case 2: General case with invalid parentheses
        String s2 = "*(()";
        assertFalse(solution.isValid(s2), "Test Case 2 Failed");

        // Test Case 3: Valid parentheses with multiple '*'
        String s3 = "(**())";
        assertTrue(solution.isValid(s3), "Test Case 3 Failed");

        // Test Case 4: Empty string
        String s4 = "";
        assertTrue(solution.isValid(s4), "Test Case 4 Failed");

        // Test Case 5: Only '*'
        String s5 = "*";
        assertTrue(solution.isValid(s5), "Test Case 5 Failed");

        // Test Case 6: Only '('
        String s6 = "(";
        assertFalse(solution.isValid(s6), "Test Case 6 Failed");

        // Test Case 7: Only ')'
        String s7 = ")";
        assertFalse(solution.isValid(s7), "Test Case 7 Failed");

        // Test Case 8: Balanced parentheses without '*'
        String s8 = "(())";
        assertTrue(solution.isValid(s8), "Test Case 8 Failed");

        // Test Case 9: Unbalanced parentheses with '*'
        String s9 = "(()*";
        assertTrue(solution.isValid(s9), "Test Case 9 Failed");

        // Test Case 10: Large input with valid parentheses
        StringBuilder s10 = new StringBuilder();
        for (int i = 0; i < 5000; i++) {
            s10.append("(");
        }
        for (int i = 0; i < 5000; i++) {
            s10.append(")");
        }
        assertTrue(solution.isValid(s10.toString()), "Test Case 10 Failed");

    }
}

/*
Better - Match all pairs
T - O(n^2) - 2n^2 + n
S - O(1)
 */
class Solution {
    public boolean isValid(String s) {
        char[] exp = s.toCharArray();
        int n = exp.length;

        for (int i = 0; i < n; i++) {
            if (exp[i] == '(') {
                boolean found = false;
                for (int j = n - 1; j > i; j--) {
                    if (exp[j] == ')') {
                        exp[i] = '|';
                        exp[j] = '|';
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    for (int j = n - 1; j > i; j--) {
                        if (exp[j] == '*') {
                            exp[i] = '|';
                            exp[j] = '|';
                            break;
                        }
                    }
                }
            }
            if (exp[i] == ')') {
                boolean found = false;
                for (int j = 0; j < i; j++) {
                    if (exp[j] == '(') {
                        exp[i] = '|';
                        exp[j] = '|';
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    for (int j = 0; j < i; j++) {
                        if (exp[j] == '*') {
                            exp[i] = '|';
                            exp[j] = '|';
                            break;
                        }
                    }
                }
            }
        }

        for (char c : exp) {
            if (c != '*' && c != '|') {
                return false;
            }
        }

        return true;
    }
}

/*
Better Simplified
T - O(n^2) - n^2 + n^2 + n
S - O(1)
 */
class Solution2 {
    public boolean isValid(String s) {
        char[] exp = s.toCharArray();
        int n = exp.length;

        // do normal match
        for (int i = 0; i < n; i++) {
            if (exp[i] == '(') {
                for (int j = n - 1; j > i; j--) {
                    if (exp[j] == ')') {
                        exp[i] = '|';
                        exp[j] = '|';
                        break;
                    }
                }
            }
        }

        // do * match
        for (int i = 0; i < n; i++) {
            if (exp[i] == '(') {
                for (int j = n - 1; j > i; j--) {
                    if (exp[j] == '*') {
                        exp[i] = '|';
                        exp[j] = '|';
                        break;
                    }
                }
            }

            if (exp[i] == ')') {
                for (int j = 0; j < i; j++) {
                    if (exp[j] == '*') {
                        exp[i] = '|';
                        exp[j] = '|';
                        break;
                    }
                }
            }
        }
        for (char c : exp) {
            if (c != '*' && c != '|') {
                return false;
            }
        }

        return true;
    }
}

/*
Brute - Recursion
T - O(n^3)
S - O(n), if memoized, O(n^2)

If no * at all, we just stop when count is negative at any given position.
When star is involved, we've to explore 3 options. Empty, (, )
Whichever gives 0 will be taken.
We stop as soon as we find valid combination
 */
class Solution3 {
    public boolean isValid(String s) {
        return isValid(s.toCharArray(), 0, 0);
    }

    private boolean isValid(char[] exp, int pos, int count) {
        if (count < 0) { // not a valid combination
            return false;
        }

        if (pos >= exp.length) {
            return count == 0; // both positive and negative at the end is invalid
        }

        boolean ans = false;
        if (exp[pos] == '(') {
            ans = isValid(exp, pos + 1, count + 1);
        } else if (exp[pos] == ')') {
            ans = isValid(exp, pos + 1, count - 1);
        } else {
            // 3 options - -1 ), 0 '', +1 (
            for (int i = -1; i <= 1; i++) {
                ans = isValid(exp, pos + 1, count + i);
                if (ans) {
                    break;
                }
            }
        }

        return ans;
    }
}

/*
Optimal
T - O(n)
S - O(1)

In normal case, when exp is balanced, balance will never go negative at any pos and at the end of exp, balance will be 0.
Now * is coming as an option. So instead of exploring all possible options, we will explore a best-case and worst-case range of balances.
If balance doen't even respect worst-case balance or best-case balance doesn't meet goals, exp is wrong.

low (worst-case) - All * are ) - If exp is correct, low will be = 0 (We won't add more ) to make low negative)
high (best-case) - All * are ( - If exp is correct, high will be >= 0

if high anytime comes below 0, like usual balance check logic, we stop and return false. Even after adding as much as (, we couldn't tally )
At the end of exp, if low > 0, return false. Even after adding as much as ), we couldn't tally (.

low is marched to reduce balance to become zero by adding ) for *
high is marched to increase balance to become >= 0 by adding ( for *
 */
class Solution4 {
    public boolean isValid(String s) {
        int worstBalance = 0, bestBalance = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') { // Normal step
                worstBalance++;
                bestBalance++;
            } else if (c == ')') { // Normal step
                worstBalance--;
                bestBalance--;
            } else {
                worstBalance--; // * as )
                bestBalance++; // * as (
            }
            if (bestBalance < 0) {
                return false; // At this point, even after adding as much as (, more ) exists
            }
            /* Actually worstBalance be -1 at this point. Gone from 0 to -1, balanced to unbalanced state.
             *(()
             At pos 1, balance is already 0, so we shouldn't add ) to make balance negative.
             Our aim is to keep the worstBalance 0 only by adding ).
             */
            if (worstBalance < 0) {
                worstBalance = 0;
            }
        }

        return worstBalance == 0; // At this point, after adding as much as ), worstBalance will be 0 or positive
    }
}