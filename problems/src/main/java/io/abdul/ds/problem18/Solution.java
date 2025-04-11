package io.abdul.ds.problem18;

import java.util.Stack;

// Infix to Postfix
class Solution {
    /**
     * 1. If operand, add it to postfix exp
     * 2. If (, add it to operator stack
     * 3. If ), pop all operator from op stack until reaching ( and pop n discard ( as well from op stack
     * 4. If operator, if precedence of top in op stack is >= current op, then pop it and add it to postfix exp.
     * Repeat until stack if empty or meet a lower precedence op and add current op to the stack
     * 5. After scanning all characters, empty stack and insert into postfix exp
     *
     * @param infix
     * @return
     */
    public String infixToPostfix(String infix) {
        char[] chars = infix.toCharArray();
        Stack<Character> opStack = new Stack<>();
        StringBuilder postfix = new StringBuilder();

        for (char theChar : chars) {
            if (theChar == ' ') {
                continue;
            }
            if ((theChar >= 'a' && theChar <= 'z') || (theChar >= 'A' && theChar <= 'Z')) {
                // 1. If operand, add it to postfix exp
                postfix.append(theChar);
            } else if (theChar == '(') {
                // 2. If (, add it to operator stack
                opStack.push(theChar);
            } else if (theChar == ')') {
                // 3. If ), pop all operator from op stack until reaching ( and pop n discard ( as well from op stack
                while (!opStack.isEmpty() && opStack.peek() != '(') {
                    postfix.append(opStack.pop());
                }
                if (!opStack.isEmpty()) {
                    opStack.pop();
                }
            } else {
                // 4. If operator, if precedence of top in op stack is >= current op, then pop it and add it to postfix exp.
                // Repeat until stack if empty or meet a lower precedence op and add current op to the stack
                while (!opStack.isEmpty() && precedence(opStack.peek()) >= precedence(theChar)) {
                    postfix.append(opStack.pop());
                }
                opStack.push(theChar);
            }
        }
        while (!opStack.isEmpty()) {
            postfix.append(opStack.pop());
        }
        return postfix.toString();
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

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println((solution.infixToPostfix(" ( ( A + B ) * ( C + E ) )")));
    }
}