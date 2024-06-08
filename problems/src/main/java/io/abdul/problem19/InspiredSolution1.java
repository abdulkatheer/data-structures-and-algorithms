package io.abdul.problem19;

/**
 * Purely based on the theme - solving sub-problems to solve the bigger whole
 */
public class InspiredSolution1 {
    public int calculate(String infix) {
        char[] chars = infix.toCharArray();
        int[] counter = new int[1];
        return solve(chars, counter);
    }

    private static int solve(char[] chars, int[] counter) {
        int result = 0;
        char operator = '+';

        while (counter[0] < chars.length) {
            char aChar = chars[counter[0]];
            counter[0] = counter[0] + 1;
            if (aChar == '+' || aChar == '-') {
                operator = aChar;
            } else if (isDigit(aChar)) {
                int digit = getDigit(chars, counter);
                result = applyOp(result, digit, operator);
            } else if (aChar == '(') {
                result = applyOp(result, solve(chars, counter), operator);
            } else if (aChar == ')') {
                return result;
            }
        }
        return result;
    }

    private static int applyOp(int op1, int op2, char operator) {
        if (operator == '+') {
            return op1 + op2;
        } else if (operator == '-') {
            return op1 - op2;
        } else {
            throw new IllegalArgumentException("Invalid operator");
        }
    }

    private static int getDigit(char[] chars, int[] counter) {
        int value = 0;
        counter[0] = counter[0] - 1;
        while (counter[0] < chars.length && isDigit(chars[counter[0]])) {
            value = (value * 10) + (chars[counter[0]] - '0');
            counter[0] = counter[0] + 1;
        }
        return value;
    }


    private static boolean isDigit(char theChar) {
        return theChar >= '0' && theChar <= '9';
    }


    public static void main(String[] args) {
        InspiredSolution1 solution = new InspiredSolution1();
//        System.out.println(solution.calculate("1 + 1"));
//        System.out.println(solution.calculate("3 - 4"));
//        System.out.println(solution.calculate("-2"));
//        System.out.println(solution.calculate("1 + 2 - 3"));
//        System.out.println(solution.calculate("-1 -2 -3"));
        // 2 - 1 - 2 + 0 -6
//        System.out.println(solution.calculate("( 1 + 1) + (3 - 4) + (-2) + (1 + 2 - 3) + (-1 -2 -3)"));
        System.out.println(solution.calculate("2147483647"));
    }
}
