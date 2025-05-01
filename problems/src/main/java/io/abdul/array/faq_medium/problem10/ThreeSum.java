package io.abdul.array.faq_medium.problem10;

import java.util.*;

// https://takeuforward.org/plus/dsa/arrays/faqs-medium/3-sum
public class ThreeSum {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();
        System.out.println(solution.threeSum(new int[]{2, -2, 0, 3, -3, 5}));
        System.out.println(solution.threeSum(new int[]{2, -1, -1, 3, -1}));
        System.out.println(solution.threeSum(new int[]{8, -6, 5, 4}));
    }
}

/*
Brute - Explore all 3 pair combinations
T - O(n^3)
S - O(1)
 */
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) { // Skip duplicates with first element
                continue;
            }
            for (int j = i + 1; j < nums.length; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) { // Skip duplicates with second element
                    continue;
                }
                int sum = nums[i] + nums[j];
                for (int k = j + 1; k < nums.length; k++) {
                    if (k > j + 1 && nums[k] == nums[k - 1]) { // Skip duplicates with third element
                        continue;
                    }
                    if (sum + nums[k] == 0) {
                        List<Integer> r = new ArrayList<>(3);
                        r.add(nums[i]);
                        r.add(nums[j]);
                        r.add(nums[k]);
                        result.add(r);
                    }
                }
            }
        }

        return result;
    }
}

/*
Better - State to maintain third element for faster lookup to reduce time complexity from n^3 to n^2
T - O(n^2) + Additional overhead in sorting every result item
S - O(n)
 */
class Solution2 {
    public List<List<Integer>> threeSum(int[] nums) {
        if (nums.length < 3) {
            return Collections.emptyList();
        }

        HashSet<List<Integer>> result = new HashSet<>();

        // Keep third expected element in HashSet with index for amortized-constant time lookup
        HashMap<Integer, Integer> thirdElement = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            thirdElement.put(-nums[i], i);
        }

        // Find two sum and the third element to make it zero
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                Integer k = thirdElement.get(nums[i] + nums[j]);
                if (k != null && i != k && j != k) {
                    List<Integer> r = new ArrayList<>(3);
                    r.add(nums[i]);
                    r.add(nums[j]);
                    r.add(nums[k]);
                    r.sort(Comparator.naturalOrder());
                    result.add(r);
                }
            }
        }

        return new ArrayList<>(result);
    }
}

/*
Optimal - Sort and reduce look time to n^2 instead of n^3
T - O(n^2)
S - O(1)

Original
2, -2, 0, 3, -3, 5
After sorting
-3,-2,0,2,3,5

i=0, j=1, k=2, sum = -5, k++
i=0, j=1, k=3, sum = -3, k++
i=0, j=1, k=4, sum = -2, k++
i=0, j=1, k=5, sum = 0, result, j++, k=j+1
i=0, j=2, k=3, sum = -1, k++
i=0, j=2, k=4, sum = 0, result, if we increase k, sum will also increase, so j++, k=j+1
i=0, j=3, k=4, sum = 2, j++, k=j+1
i=0, j=4, k=5, sum = 5, i++, j=i+1, k=j+1
i=1, j=2, k=3, sum = 0, result, j++, k=j+1
i=1, j=3, k=4, sum = 3, j++, k=j+1
i=1, j=4, k=5, sum = 6, i++, j=i+1, k=j+1
i=2, j=3, k=4, sum = 5, j++, k=j+1
i=2, j=4, k=5, sum = 8, i++, j=i+1, k=j+1
i=3, j=4, k=5, sum = 10 -> end
 */
class Solution3 {
    public List<List<Integer>> threeSum(int[] nums) {
        if (nums.length < 3) {
            return Collections.emptyList();
        }

        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) { // Skip duplicates with first element
                continue;
            }
            int j = i + 1;
            int k = nums.length - 1;
            while (j < k) { // both j and k going in same direction will add difficulty in incrementing and checking for bounds
                if (j > i + 1 && nums[j] == nums[j - 1]) { // Skip duplicates with second element
                    j++;
                    continue;
                }
                if (k < nums.length - 1 && nums[k] == nums[k + 1]) { // Skip duplicates with third   element
                    k--;
                    continue;
                }
                int sum = nums[i] + nums[j] + nums[k];
                if (sum == 0) {
                    List<Integer> r = new ArrayList<>(3);
                    r.add(nums[i]);
                    r.add(nums[j]);
                    r.add(nums[k]);
                    result.add(r);
                    j++;
                } else if (sum < 0) {
                    j++;
                } else {
                    k--;
                }
            }
        }

        return result;
    }
}