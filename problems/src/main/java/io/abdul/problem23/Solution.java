package io.abdul.problem23;

// https://leetcode.com/problems/parse-lisp-expression/

import java.util.HashMap;
import java.util.Stack;

/**
 * 1) let v1 x1 v2 x2 ... vn xn expression -> left to right, expression is the result of the whole let
 * 2) mult e1 e2 -> e1 * e2
 * 3) add e1 e2  -> e1 + e2
 * 4) e can be again let or mult or add
 * <p>
 * Main problem is scope!
 */
class Solution {
    public int evaluate(String expression) {
        return solve(expression.toCharArray(), new int[1], new Stack<>());
    }

    // (let x 2 (mult x (let x 3 y 4 (add x y))))
    // (let (add 1 2))

    /**
     * 1. (let (add 1 2)) -> (let 3) -> 3
     * 2. (add 1 2)) -> 3)
     * 3. 1 2))
     * 4. 2))
     */
    private static int solve(char[] tokens, int[] index, Stack<HashMap<String, Integer>> scope) {
        // Base case - if variable - parse variable and return value from scope
        // Base case - if literal - parse number and return value
        // Recursive case - if (, solve expression
        if (Character.isLowerCase(tokens[index[0]])) { // Base case - variable
            StringBuilder var = new StringBuilder();
            while (Character.isLowerCase(tokens[index[0]]) || Character.isDigit(tokens[index[0]])) { // considering a var name can have digits as well
                var.append(tokens[index[0]]);
                index[0] = index[0] + 1;
            }
            return resolve(var.toString(), scope); // scope resolution
        } else if (Character.isDigit(tokens[index[0]]) || tokens[index[0]] == '-') { // Base case - literal
            if (tokens[index[0]] == '-') { // Negative number
                index[0] = index[0] + 1; // eating - sign
                int value = 0;
                char c = tokens[index[0]];
                while (Character.isDigit(tokens[index[0]])) {
                    value = value * 10 + (tokens[index[0]] - '0');
                    index[0] = index[0] + 1;
                }
                return -value;
            } else {
                int value = 0;
                char c = tokens[index[0]];
                while (Character.isDigit(tokens[index[0]])) {
                    value = value * 10 + (tokens[index[0]] - '0');
                    index[0] = index[0] + 1;
                }
                return value;
            }

        } else if (tokens[index[0]] == '(') { // Expression
            scope.push(new HashMap<>()); // add new scope for this expression
            index[0] = index[0] + 1; // eating (
            if (tokens[index[0]] == 'l') { // let
                index[0] = index[0] + 4; // eating let%s
                while (true) { // current let's end
                    if (Character.isLowerCase(tokens[index[0]])) {
                        StringBuilder var = new StringBuilder();
                        while (Character.isLowerCase(tokens[index[0]]) || Character.isDigit(tokens[index[0]])) { // considering a var name can have digits as well
                            var.append(tokens[index[0]]);
                            index[0] = index[0] + 1;
                        }
                        if (tokens[index[0]] == ')') { // end of let
                            int value = resolve(var.toString(), scope); // scope resolution
                            index[0] = index[0] + 1; // eat )
                            scope.pop(); // pop scope of this expression
                            return value;
                        }
                        index[0] = index[0] + 1; // eating space
                        HashMap<String, Integer> currentScope = scope.peek();
                        currentScope.put(var.toString(), solve(tokens, index, scope));
                        index[0] = index[0] + 1; // eating space
                    } else { // expression
                        int solve = solve(tokens, index, scope);
                        index[0] = index[0] + 1; // eat )
                        scope.pop(); // pop scope of this expression
                        return solve;
                    }
                }
            } else if (tokens[index[0]] == 'm') { // mult
                index[0] = index[0] + 5;
                int v1 = solve(tokens, index, scope);
                index[0] = index[0] + 1; // eating space
                int v2 = solve(tokens, index, scope);
                index[0] = index[0] + 1; // eat )
                scope.pop(); // pop scope of this expression
                return v1 * v2;
            } else if (tokens[index[0]] == 'a') { // add
                index[0] = index[0] + 4;
                int v1 = solve(tokens, index, scope);
                index[0] = index[0] + 1; // eating space
                int v2 = solve(tokens, index, scope);
                index[0] = index[0] + 1; // eat )
                scope.pop(); // pop scope of this expression
                return v1 + v2;
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static int resolve(String variable, Stack<HashMap<String, Integer>> stack) {
        for (int i = stack.size() - 1; i >= 0; i--) {
            HashMap<String, Integer> scope = stack.elementAt(i);
            if (scope.containsKey(variable)) {
                return scope.get(variable);
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.evaluate("(let (add 5 2))"));
//        System.out.println(solution.evaluate("(let a 10 (add a 2))"));
//        System.out.println(solution.evaluate("(let a -10 (add a 2))"));
        /**
         * x - 10/20
         * y - 20/30
         * z - 30
         * ret - 30
         */
//        System.out.println(solution.evaluate("(let x 10 y (let x 20 x) z (let y 30 y) y)"));
        /*
        (let x 2 (mult x (let x 3 y 4 (add x y))))
        (mult x (let x 3 y 4 (add x y)))
        (let x 3 y 4 (add x y))
        (add x y)
         */
//        System.out.println(solution.evaluate("(let x 2 (mult x (let x 3 y 4 (add x y))))"));
//        System.out.println(solution.evaluate("(let a1 3 b2 (add a1 1) b2)"));
        System.out.println(solution.evaluate("(let x2 13 x4 -5 (add x2 x4))"));
    }
}