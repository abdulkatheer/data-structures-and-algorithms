package io.abdul.binary_search.on_answers.problem4;

public class KokaEatingBananas {
    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.minimumRateToEatBananas(new int[]{7, 15, 6, 3}, 8));
//        System.out.println(solution.minimumRateToEatBananas(new int[]{25, 12, 8, 14, 19}, 5));
//        System.out.println(solution.minimumRateToEatBananas(new int[]{3, 7, 6, 11}, 8));
//        System.out.println(solution.minimumRateToEatBananas(new int[]{240, 528, 598, 618, 812, 206, 274, 406, 852, 930, 883, 763, 152, 29, 609, 883, 406, 665, 111, 370, 844, 664, 756, 295, 36, 974, 245}, 211));
        System.out.println(solution.minimumRateToEatBananas(new int[]{805306368, 805306368, 805306368}, 1000000000));
    }
}

/*
Optimal
T - O(n log(max(nums))
S - O(1)

Same as smallestDivisor
 */
class Solution {
    public int minimumRateToEatBananas(int[] nums, int h) {
        int max = Integer.MIN_VALUE;
        for (int num : nums) {
            max = Math.max(num, max);
        }

        int result = 1;
        int low = 1, high = max;
        while (low <= high) {
            int mid = (low + high) / 2;

            long sumDivisions = sumDivisions(nums, mid);

            if (sumDivisions <= h) { // Increase by reducing divisor
                high = mid - 1;
                result = mid;
            } else {
                low = mid + 1;
            }
        }

        return result;
    }

    private long sumDivisions(int[] nums, int d) {
        long divisions = 0;
        for (int num : nums) {
            divisions += (int) Math.ceil((double) num / d);
        }
        return divisions;
    }
}