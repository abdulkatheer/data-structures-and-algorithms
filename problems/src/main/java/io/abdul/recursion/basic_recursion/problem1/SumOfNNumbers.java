package io.abdul.recursion.basic_recursion.problem1;

public class SumOfNNumbers {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.NnumbersSum(10));
        System.out.println(solution.NnumbersSum(0));
        System.out.println(solution.NnumbersSum(1));
        System.out.println(solution.NnumbersSum(2));
    }
}

class Solution {
    public int NnumbersSum(int N) {
        if (N == 0) {
            return 0;
        }
        return N + NnumbersSum(N - 1);
    }
}