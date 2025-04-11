package io.abdul.recursion.implementation_problems.problem2;

import java.util.ArrayList;
import java.util.List;

// https://takeuforward.org/plus/dsa/recursion/implementation-problems/generate-paranthesis
public class GenerateParanthesis {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.generateParenthesis(1));
        System.out.println(solution.generateParenthesis(2));
        System.out.println(solution.generateParenthesis(3));
    }
}

/*
Recursion and backtracking

Case 1: n=1
Always have to start with right paranthesis
(
Only one option left to choose
)

Case 2: n=2
(, ()) left
((, )) left
((), ) left
(()), none left
(, ()) left
(), () left
()(, ) left (though you had two options you can choose only right)
()(), none left

Maintain count left and right
1) If left and right are zero, all possible parentheses used. So add to result
2) If left and right are equal, expression is already balanced, so we can't choose left parentheses and only right can be chosen. Choose right and go recursively.
3) Otherwise, if left exists, add left and go recursively. If right exists, add right and go recursively.
 */
class Solution {
    /*
    T - O( 4^n / sqrt(n) )
    S - O(n) (Excluded the result space, which takes O (4^n / sqrt(n) )
     */
    public List<String> generateParenthesis(int n) {
        ArrayList<String> result = new ArrayList<>();
        generateParenthesis(new char[n * 2], n, n, 0, result);
        return result;
    }

    private void generateParenthesis(char[] buffer, int left, int right, int i, List<String> result) {
        if (left == 0 && right == 0) { // One permutation achieved
            result.add(new String(buffer));
            return;
        }
        if (left - right == 0) { // Balanced
            buffer[i] = '(';
            generateParenthesis(buffer, left, right - 1, i + 1, result);
        } else {
            if (left > 0) {
                buffer[i] = ')';
                generateParenthesis(buffer, left - 1, right, i + 1, result);
            }
            if (right > 0) {
                buffer[i] = '(';
                generateParenthesis(buffer, left, right - 1, i + 1, result);
            }
        }
    }
}