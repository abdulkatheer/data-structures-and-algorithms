package io.abdul.array.faq_hard.problem1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
We can't have more than one element with >n/2 frequency
 */
public class MajorityElementI {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();
        System.out.println(solution.majorityElement(new int[]{7, 0, 0, 1, 7, 7, 2, 7, 7}));
        System.out.println(solution.majorityElement(new int[]{1, 1, 1, 2, 1, 2}));
        System.out.println(solution.majorityElement(new int[]{-1, -1, -1, -1}));
        System.out.println(solution.majorityElement(new int[]{-1, -1}));
        System.out.println(solution.majorityElement(new int[]{-1}));
    }
}

/*
Brute
T - O(n)
S - O(n)
 */
class Solution {
    public int majorityElement(int[] nums) {
        HashMap<Integer, Integer> countsByNum = new HashMap<>();

        for (int num : nums) {
            Integer count = countsByNum.getOrDefault(num, 0);
            countsByNum.put(num, count + 1);
        }

        int maxCount = 0;
        int maxNum = nums[0];
        for (Map.Entry<Integer, Integer> countNumEntry : countsByNum.entrySet()) {
            if (countNumEntry.getValue() > maxCount) {
                maxCount = countNumEntry.getValue();
                maxNum = countNumEntry.getKey();
            }
        }

        return maxNum;
    }
}

/*
Better - Sort to avoid additional space
T - O(n logn)
S - O(1)

7, 0, 0, 1, 7, 7, 2, 7, 7 -> 0 0 1 2 7 7 7 7 7
 */
class Solution2 {
    public int majorityElement(int[] nums) {
        Arrays.sort(nums);

        int num = nums[0];
        int count = 1;
        int maxCount = count;
        int maxNum = num;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == num) {
                count++;
            } else {
                if (count > maxCount) {
                    maxCount = count;
                    maxNum = num;
                }
                num = nums[i];
                count = 1;
            }
        }


        return count > maxCount ? num : maxNum;
    }
}

/*
Optimal - One popular element in moving window approach
T - O(n)
S - O(1)
 */
class Solution3 {
    public int majorityElement(int[] nums) {
        int popularity = 0;
        int num = -1;

        // Find popular (> n/2 count) in a moving window
        // Last window will have at least one popular (as per the problem)
        for (int n : nums) {
            if (popularity == 0) { // No one is popular so far
                num = n;
                popularity = 1;
            } else if (n == num) { // Popular person so far is getting more popular
                popularity++;
            } else { // Popular person so far is not so popular as count increases
                popularity--;
            }
        }

        // confirm if the found popular element is really popular in overall array and not just in one window
        int count = 0;
        for (int n : nums) {
            if (num == n) {
                count++;
            }
        }

        return count > (num) / 2 ? num : -1;
    }
}