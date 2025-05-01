package io.abdul.array.faq_medium.problem6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// https://takeuforward.org/plus/dsa/arrays/faqs-medium/pascals-triangle-ii
public class PascalsTriangleII {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution4 solution = new Solution4();
        System.out.println(Arrays.toString(solution.pascalTriangleII(1)));
        System.out.println(Arrays.toString(solution.pascalTriangleII(2)));
        System.out.println(Arrays.toString(solution.pascalTriangleII(3)));
        System.out.println(Arrays.toString(solution.pascalTriangleII(4)));
        System.out.println(Arrays.toString(solution.pascalTriangleII(5)));
        System.out.println(Arrays.toString(solution.pascalTriangleII(6)));
        System.out.println(Arrays.toString(solution.pascalTriangleII(7)));
        System.out.println(Arrays.toString(solution.pascalTriangleII(8)));
    }
}

class Solution {
    public int[] pascalTriangleII(int r) {
        List<List<Integer>> pascalsTriangle = new ArrayList<>(r);
        pascalsTriangle.add(List.of(1));

        for (int i = 1; i < r; i++) {
            List<Integer> prevRow = pascalsTriangle.get(i - 1);
            List<Integer> currRow = new ArrayList<>(i + 2);
            currRow.add(1);
            for (int j = 0; j < prevRow.size() - 1; j++) {
                currRow.add(prevRow.get(j) + prevRow.get(j + 1));
            }
            currRow.add(1);
            pascalsTriangle.add(currRow);
        }

        return pascalsTriangle.get(r - 1).stream().mapToInt(i -> i).toArray();
    }
}

class Solution2 {
    public int[] pascalTriangleII(int r) {
        int[] temp = new int[r];
        int[] result = new int[r];

        result[0] = 1;

        for (int i = 1; i < r; i++) {
            temp[0] = 1;
            for (int j = 0; j < r - 2; j++) {
                temp[j + 1] = result[j] + result[j + 1];
            }
            temp[r - 1] = 1;

            int[] t = result;
            result = temp;
            temp = t;
        }

        return result;
    }
}

/* Using Combination formula
nCr = n! / r! * (n-r)!
 */
class Solution3 {
    public int[] pascalTriangleII(int r) {
        if (r == 1) {
            return new int[]{1};
        }
        r = r - 1;
        int[] result = new int[r + 1];

        int nFactorial = 1;
        for (int j = 1; j <= r; j++) {
            nFactorial *= j;
        }

        result[0] = 1;
        for (int i = 1; i <= r; i++) {
            int rFactorial = 1;
            int nMinusRFactorial = 1;

            for (int j = 1; j <= i; j++) {
                rFactorial *= j;
            }

            for (int j = 1; j <= r - i; j++) {
                nMinusRFactorial *= j;
            }
            result[i] = nFactorial / (rFactorial * nMinusRFactorial);
        }

        return result;
    }
}

/* Using Combination formula - Optimized
nCr = n! / r! * (n-r)!
n=5, r=1 => 5x4x3x2x1 / (1) x (4x3x2x1) => 4x3x2x1 in cancelled => 5 / (1) x ()
n=5, r=2 => 5x4x3x2x1 / (1x2) x (3x2x1) => 3x2x1 in cancelled => 5x4 / (1x2) x ()
n=5, r=3 => 5x4x3x2x1 / (1x2x3) x (2x1) => 2x1 in cancelled => 5x4x3 / (1x2x3) x ()
n=5, r=4 => 5x4x3x2x1 / (1x2x3x4) x (1) => 2x1 in cancelled => 5x4x3x2 / (1x2x3x4) x ()
n=5, r=5 => 5x4x3x2x1 / (1x2x3x4x5) x (1)
 */
class Solution4 {
    public int[] pascalTriangleII(int r) {
        if (r == 1) {
            return new int[]{1};
        }
        r = r - 1; // Bcz problem considers 0th row as first, whereas combination works only from 1
        int[] result = new int[r + 1];
        result[0] = 1;

        for (int i = 1; i <= r; i++) {
            int combination = 1;
            for (int j = 0; j < i; j++) {
                combination = combination * (r - j);
                combination = combination / (j + 1);
            }
            result[i] = combination;
        }

        return result;
    }
}