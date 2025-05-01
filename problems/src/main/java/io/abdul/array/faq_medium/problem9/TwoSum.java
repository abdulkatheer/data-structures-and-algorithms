package io.abdul.array.faq_medium.problem9;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

// https://takeuforward.org/plus/dsa/arrays/faqs-medium/two-sum
public class TwoSum {
    public static void main(String[] args) {
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();
        System.out.println(Arrays.toString(solution.twoSum(new int[]{1, 6, 2, 10, 3}, 7)));
        System.out.println(Arrays.toString(solution.twoSum(new int[]{1, 3, 5, -7, 6, -3}, 0)));
        System.out.println(Arrays.toString(solution.twoSum(new int[]{-6, 7, 1, -7, 6, 2}, 3)));
    }
}

/*
Brute - Try all possible sums
T - O(n^2)
S - O(1)
 */
class Solution {
    public int[] twoSum(int[] nums, int target) {
        int[] result = new int[]{-1, -1};

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    result[0] = i;
                    result[1] = j;
                }
            }
        }

        return result;
    }
}

/*
Better - Additional space to have constant (amortized) time pair lookup
T - O(n)
S - O(n)
 */
class Solution2 {
    public int[] twoSum(int[] nums, int target) {
        int[] result = new int[]{-1, -1};
        HashMap<Integer, Integer> sum = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            sum.put(target - nums[i], i);
        }

        for (int i = 0; i < nums.length; i++) {
            Integer pos = sum.get(nums[i]);
            if (pos != null) {
                if (pos != i) {
                    result[0] = Math.min(i, pos);
                    result[1] = Math.max(i, pos);
                    break;
                }
            }
        }

        return result;
    }
}

/*
Optimal - Sorting and additional space with constant lookup; Two pointer approach
T - O(n x log n)
S - O(n)

00 01 -> num and index
10 11
20 21
 */
class Solution3 {
    public int[] twoSum(int[] nums, int target) {
        int n = nums.length;
        int[][] elementAndIndex = new int[nums.length][2];

        for (int i = 0; i < n; i++) {
            elementAndIndex[i][0] = nums[i];
            elementAndIndex[i][1] = i;
        }

        // Sort elementAndIndex based on value
        Arrays.sort(elementAndIndex, Comparator.comparingInt(o -> o[0]));

        int left = 0;
        int right = n - 1;
        int[] result = new int[]{-1, -1};

        while (left < right) {
            int sum = elementAndIndex[left][0] + elementAndIndex[right][0];
            if (sum < target) {
                left++;
            } else if (sum > target) {
                right--;
            } else {
                result[0] = Math.min(elementAndIndex[left][1], elementAndIndex[right][1]);
                result[1] = Math.max(elementAndIndex[left][1], elementAndIndex[right][1]);
                break;
            }
        }

        return result;
    }
}