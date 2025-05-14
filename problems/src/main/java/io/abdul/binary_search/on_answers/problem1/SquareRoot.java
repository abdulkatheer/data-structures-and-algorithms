package io.abdul.binary_search.on_answers.problem1;

public class SquareRoot {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.floorSqrt(36));
        System.out.println(solution.floorSqrt(28));
        System.out.println(solution.floorSqrt(50));
    }
}

/*
Optimal - Largest number such that x^2<=n
T - O(log n)
S - O(1)
 */
class Solution {
    public long floorSqrt(long n) {
        long low = 1, high = n, sqrt = 0;

        while (low <= high) {
            long mid = (low + high) / 2;
            long square = mid * mid;
            if (square <= n) { // Found a candidate, now look for better one
                sqrt = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return sqrt;
    }
}