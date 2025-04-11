package io.abdul.array.two_pointer_technique.problem2;

import java.util.*;

// https://takeuforward.org/data-structure/3-sum-find-triplets-that-add-up-to-a-zero/
public class ThreeSum {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution3a solution = new Solution3a();
        System.out.println(solution.threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
        System.out.println(solution.threeSum(new int[]{0, 0, 0}));
    }
}

// Brute-force
// Time - O(n^3) ?? Too much time
// Time required for sorting is constant as it gonna sort 3 element always
// Space - O(n)
// Space 2O(t); O(t) for Set and O(t) for list, where t is the number of triplets
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Set<List<Integer>> result = new HashSet<>();
        for (int i = 0; i < nums.length - 2; i++) {
            for (int j = i + 1; j < nums.length - 1; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> r = new ArrayList<>();
                        r.add(nums[i]);
                        r.add(nums[j]);
                        r.add(nums[k]);
                        r.sort(Comparator.naturalOrder());
                        result.add(r);
                    }
                }
            }
        }
        return new ArrayList<>(result);
    }
}

/*
Better Solution
Time - O(n^2) as we iterate k for every i and do reverse math and HashSet to calculate mid number
Space - O(n) to store mid numbers, 2O(n) to store list and set
 */
class Solution2 {
    public List<List<Integer>> threeSum(int[] nums) {

        Set<List<Integer>> result = new HashSet<>();

//        int i = 0;
//        int j = i + 2;
//        HashSet<Integer> mids = new HashSet<>();
//        mids.add(nums[i + 1]);
//        while (i < nums.length - 2) {
//            int numNeeded = -1 * (nums[i] + nums[j]);
//            if (mids.contains(numNeeded)) { // match found
//                List<Integer> triplets = new ArrayList<>(3);
//                triplets.add(nums[i]);
//                triplets.add(numNeeded);
//                triplets.add(nums[j]);
//                triplets.sort(Comparator.naturalOrder());
//                result.add(triplets);
//            }
//            mids.add(nums[j]);
//            j++;
//            if (j >= nums.length - 1) { // iteration for i is completed, start next i
//                i++;
//                j = i + 2;
//                mids = new HashSet<>();
//            }
//        }

        for (int i = 0; i < nums.length - 2; i++) {
            HashSet<Integer> mids = new HashSet<>();
            mids.add(nums[i + 1]); // add first mid
            for (int k = i + 2; k < nums.length; k++) {
                int numNeeded = -1 * (nums[i] + nums[k]); // j
                if (mids.contains(numNeeded)) { // match found
                    List<Integer> triplets = new ArrayList<>(3);
                    triplets.add(nums[i]);
                    triplets.add(numNeeded);
                    triplets.add(nums[k]);
                    triplets.sort(Comparator.naturalOrder());
                    result.add(triplets);
                }
                mids.add(nums[k]);
            }
        }

        return new ArrayList<>(result);
    }
}

/*
Optimal Solution - Two Pointer approach
By sorting the array, we can find the zero-sum by expanding or shrinking as the nums are sorted
Time - O(n logn) for sorting + O(n^2) for iteration
Space - O(1)
 */
class Solution3 {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        Set<List<Integer>> result = new HashSet<>();
        for (int i = 0; i < nums.length - 2; i++) {
            int j = i + 1;
            int k = nums.length - 1;
            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                if (sum == 0) {
                    List<Integer> triplets = new ArrayList<>(3);
                    triplets.add(nums[i]);
                    triplets.add(nums[j]);
                    triplets.add(nums[k]); // don't have to sort as i,j,k are already sorted
                    result.add(triplets);
                    j++; // Increase to check next element
                } else if (sum < 0) { // Increase to reach zero
                    j++;
                } else { // Increase to reach zero
                    k--;
                }
            }
        }
        return new ArrayList<>(result);
    }
}

// Avoiding Set
class Solution3a {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue; // if is same as prev, then gonna result in same answers and may result in duplicate triplets
            }
            int j = i + 1;
            int k = nums.length - 1;
            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                if (sum == 0) {
                    List<Integer> triplets = new ArrayList<>(3);
                    triplets.add(nums[i]);
                    triplets.add(nums[j]);
                    triplets.add(nums[k]); // don't have to sort as i,j,k are already sorted
                    result.add(triplets);
                    j++; // Increase to check next element
                    k--; // Decrease as well to avoid duplicates
                    // If nums[j] or nums[k] are same as prev value, Gonna result in same answer as before and will result in duplicate if sum is zero
                    // We've used nums[j] and nums[k] for calc
                    // SO as long as j and k are same, we'll get same sum. So skip all same j and k positions before going to next iteration
                    while (j < k && nums[j] == nums[j - 1]) {
                        j++; // Increase to skip duplicates
                    }
                    while (j < k && nums[k] == nums[k + 1]) {
                        k--; // Decrease to skip duplicates
                    }
                } else if (sum < 0) { // Increase to reach zero
                    j++;
                } else { // Increase to reach zero
                    k--;
                }
            }
        }
        return result;
    }
}