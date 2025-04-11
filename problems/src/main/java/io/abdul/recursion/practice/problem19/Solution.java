package io.abdul.recursion.practice.problem19;

import java.util.Stack;

// https://leetcode.com/problems/basic-calculator/description/
// Tags: Math, String, Stack, Recursion

/**
 * No operator precedence issue, just + & -
 * Handle negative values
 * Handle sub problems in ()
 */
public class Solution {
    public int calculate(String infix) {
        // infix to postfix with evaluation

        char[] chars = infix.toCharArray();
        Stack<Character> opStack = new Stack<>();
        Stack<Integer> valueStack = new Stack<>();

        for (int i = 0; i < chars.length; i++) {
            char theChar = chars[i];
            if (theChar == ' ') {
                continue;
            }
            if (isDigit(theChar)) {
                int value = 0;
                // 1. If operand, add it to postfix exp
                // Handling multiple digits
                while (i < chars.length && isDigit(chars[i])) {
                    value = (value * 10) + Integer.parseInt(String.valueOf(chars[i]));
                    i++;
                }
                valueStack.push(value);
                // right now the i points to
                // the character next to the digit,
                // since the for loop also increases
                // the i, we would skip one
                //  token position; we need to
                // decrease the value of i by 1 to
                // correct the offset.
                i--;
            } else if (theChar == '(') {
                // 2. If (, add it to operator stack
                opStack.push(theChar);
            } else if (theChar == ')') {
                // 3. If ), pop all operator from op stack until reaching ( and pop n discard ( as well from op stack
                while (!opStack.isEmpty() && opStack.peek() != '(') {
                    doCalculate(valueStack, opStack);
                }
                if (!opStack.isEmpty()) {
                    opStack.pop();
                }
            } else {
                // Handling minus values
                if (theChar == '-' && (i == 0 || (!opStack.isEmpty() && opStack.peek() == '('))) { // it's a negative value, not an operator
                    int value = 0;
                    i++;
                    while (i < chars.length && isDigit(chars[i])) {
                        value = (value * 10) + Integer.parseInt(String.valueOf(chars[i]));
                    }
                    valueStack.push(-value);
                    i--;
                } else {
                    // 4. If operator, if precedence of top in op stack is >= current op, then pop it and add it to postfix exp.
                    // Repeat until stack if empty or meet a lower precedence op and add current op to the stack
                    while (!opStack.isEmpty() && precedence(opStack.peek()) >= precedence(theChar)) {
                        doCalculate(valueStack, opStack);
                    }
                    opStack.push(theChar);
                }
            }
        }
        while (!opStack.isEmpty()) {
            doCalculate(valueStack, opStack);
        }
        return valueStack.pop();
    }

    private static void doCalculate(Stack<Integer> valueStack, Stack<Character> opStack) {
        int operand2 = valueStack.pop();
        int operand1 = valueStack.pop();
        char operator = opStack.pop();
        valueStack.push(applyOp(operand1, operand2, operator));
    }

    private static int precedence(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        } else if (operator == '/' || operator == '*') {
            return 2;
        } else {
            return -1;
        }
    }

    private static int applyOp(int a, int b, char op) {
        return switch (op) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> a / b;
            default -> throw new IllegalArgumentException("Invalid operator");
        };
    }

    private static boolean isDigit(char theChar) {
        return theChar >= '0' && theChar <= '9';
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.calculate("1 + 1"));
//        System.out.println(solution.calculate(" 2-1 + 2 "));
        System.out.println(solution.calculate("(1+(4+5+2)-3)+(6+8)"));
        System.out.println(solution.calculate("(11 +( 42 +54+21)-33)+( 61 + 82 )"));
    }
}
