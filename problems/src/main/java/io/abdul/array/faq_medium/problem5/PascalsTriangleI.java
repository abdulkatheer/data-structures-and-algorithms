package io.abdul.array.faq_medium.problem5;

import java.util.ArrayList;
import java.util.List;

// https://takeuforward.org/plus/dsa/arrays/faqs-medium/pascals-triangle-i
public class PascalsTriangleI {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.pascalTriangleI(6, 3));
        System.out.println(solution.pascalTriangleI(4, 2));
        System.out.println(solution.pascalTriangleI(5, 3));
        System.out.println(solution.pascalTriangleI(14, 12));

        Solution2 solution2 = new Solution2();
        System.out.println(solution2.pascalTriangleI(6, 3));
        System.out.println(solution2.pascalTriangleI(4, 2));
        System.out.println(solution2.pascalTriangleI(14, 12));

        Solution3 solution3 = new Solution3();
        System.out.println(solution3.pascalTriangleI(6, 3));
        System.out.println(solution3.pascalTriangleI(4, 2));
        System.out.println(solution3.pascalTriangleI(14, 12));

        Solution4 solution4 = new Solution4();
        System.out.println(solution4.pascalTriangleI(6, 3));
        System.out.println(solution4.pascalTriangleI(4, 2));
        System.out.println(solution4.pascalTriangleI(14, 12));
    }
}

// Build entire triangle and store everything
class Solution {
    public int pascalTriangleI(int r, int c) {
        r = r - 1; // Bcz problem includes base row as well, but actual combinations should not include base row
        c = c - 1; // Bcz problem includes first 0th column as well
        List<List<Integer>> pascalsTriangle = new ArrayList<>();
        pascalsTriangle.add(List.of(0, 1, 0)); // Base

        for (int i = 1; i <= r; i++) {
            List<Integer> prevRow = pascalsTriangle.get(i - 1);// Prev row
            List<Integer> currentRow = new ArrayList<>(i + 3);
            currentRow.add(0);
            for (int j = 0; j < prevRow.size() - 1; j++) {
                currentRow.add(prevRow.get(j) + prevRow.get(j + 1));
            }
            currentRow.add(0);
            pascalsTriangle.add(currentRow);
        }

        return pascalsTriangle.get(r).get(c + 1);
    }
}

// Build entire triangle and store only last 2 which is needed for calculations and result
class Solution2 {
    public int pascalTriangleI(int r, int c) {
        r = r - 1; // Bcz problem includes base row as well, but actual combinations should not include base row
        c = c - 1; // Bcz problem includes first 0th column as well
        int[] temp = new int[r + 4]; // for odd rows
        int[] result = new int[r + 4]; // for even rows

        result[0] = 0; // Base
        result[1] = 1;
        result[2] = 0;

        for (int i = 1; i <= r; i++) {
            int size = i + 3;
            temp[0] = 0;
            for (int k = 0, j = 1; k < size - 1; k++, j++) {
                temp[j] = result[k] + result[k + 1];
            }
            temp[size - 1] = 0;
            int[] t = result;
            result = temp;
            temp = t;
        }

        return result[c + 1];
    }
}

/*
Using Combination formula
n - total elements
r - elements to be selected
nCr - > how many times r elements can be picked out of n elements
nCr = n! / r! x (n-r)!
 */
class Solution3 {
    public int pascalTriangleI(int r, int c) {
        r = r - 1; // Bcz problem includes base row as well, but actual combinations should not include base row
        c = c - 1; // Bcz problem includes first 0th column as well
        int nFactorial = 1; // 0!
        int rFactorial = 1;
        int nMinusRFactorial = 1;

        for (int i = 1; i <= r; i++) {
            nFactorial *= i;
        }

        for (int i = 1; i <= c; i++) {
            rFactorial *= i;
        }

        for (int i = 1; i <= r - c; i++) {
            nMinusRFactorial *= i;
        }

        return nFactorial / (rFactorial * nMinusRFactorial);
    }
}

/*
Using Combination formula - Optimized
nCr = n! / r! x (n-r)!

n=6, r=2 6*5*4*3*2*1 / 2*1 x 4*3*2*1, here 4*3*2*1 is cancelled -> 6*5 / 2*1
n=6, r=3 6*5*4*3*2*1 / 3*2*1 x 3*2*1, here 3*2*1 is cancelled -> 6*5*4 / 3*2*1
n=6, r=4 6*5*4*3*2*1 / 4*3*2*1 x 2*1, here 2*1 is cancelled -> 6*5*4*3 / 4*3*2*1
n=6, r=5 6*5*4*3*2*1 / 5*4*3*2*1 x 1, here 1 is cancelled -> 6*5*4*3*2 / 5*4*3*2*1
 */
class Solution4 {
    public int pascalTriangleI(int r, int c) {
        r = r - 1; // Bcz problem includes base row as well, but actual combinations should not include base row
        c = c - 1; // Bcz problem includes first 0th column as well
        int result = 1;
        for (int i = 0; i < c; i++) {
            result = result * (r - i); // Divide then and there to avoid int overflow
            result = result / (i + 1); // divide from 1,2,3... to avoid rounding
        }

        return result;
    }
}

