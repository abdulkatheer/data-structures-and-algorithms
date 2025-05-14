package io.abdul.binary_search.on_answers.problem3;

public class SmallestDivisor {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.smallestDivisor(new int[]{1, 2, 3, 4, 5}, 8));
        System.out.println(solution.smallestDivisor(new int[]{8, 4, 2, 3}, 10));
        System.out.println(solution.smallestDivisor(new int[]{8, 4, 2, 3}, 4));
    }
}

/*
Optimal - BS from 1 to max(nums)
T - O(n log max(nums))
S - O(1)
 */
class Solution {
    public int smallestDivisor(int[] nums, int limit) {
        int max = Integer.MIN_VALUE;
        for (int num : nums) {
            max = Math.max(max, num);
        }

        int low = 1, high = max, divisor = 1;

        while (low <= high) {
            int mid = (low + high) / 2;

            int sumDivisions = sumDivisions(nums, mid);
            if (sumDivisions <= limit) { // Matched, look for better by decreasing divisor
                divisor = mid;
                high = mid - 1;
            } else { // Reduce by increasing divisor
                low = mid + 1;
            }
        }

        return divisor;
    }

    private int sumDivisions(int[] nums, int div) {
        int result = 0;
        for (int num : nums) {
            result += (int) Math.ceil((double) num / div);
        }
        return result;
    }
}
