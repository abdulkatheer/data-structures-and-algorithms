package io.abdul.array.faq_medium.problem7;

import java.util.ArrayList;
import java.util.List;

public class PascalsTriangleIII {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.pascalTriangleIII(1));
        System.out.println(solution.pascalTriangleIII(2));
        System.out.println(solution.pascalTriangleIII(3));
        System.out.println(solution.pascalTriangleIII(4));
        System.out.println(solution.pascalTriangleIII(5));
        System.out.println(solution.pascalTriangleIII(6));
    }
}

class Solution {
    public List<List<Integer>> pascalTriangleIII(int n) {
        List<List<Integer>> pascalsTriangle = new ArrayList<>();
        pascalsTriangle.add(List.of(1));

        for (int i = 1; i < n; i++) {
            List<Integer> row = new ArrayList<>();
            List<Integer> prevRow = pascalsTriangle.get(i - 1);
            row.add(1);
            for (int j = 0; j < prevRow.size() - 1; j++) {
                row.add(prevRow.get(j) + prevRow.get(j + 1));
            }
            row.add(1);
            pascalsTriangle.add(row);
        }
        return pascalsTriangle;
    }
}
