package io.abdul.array.faq_medium.problem11;

import java.util.*;

// https://takeuforward.org/plus/dsa/arrays/faqs-medium/4-sum
public class FourSum {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();
        System.out.println(solution.fourSum(new int[]{1, -2, 3, 5, 7, 9}, 7));
        System.out.println(solution.fourSum(new int[]{7, -7, 1, 2, 14, 3}, 9));
        System.out.println(solution.fourSum(new int[]{1, 1, 3, 4, -3}, 5));
    }
}

/*
Brute - Find all possible 4-sum
T - O(n^4)
S - O(1)
 */
class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        if (nums.length < 4) {
            return Collections.emptyList();
        }

        HashSet<List<Integer>> fourSum = new HashSet<>();
        for (int a = 0; a < nums.length; a++) {
            for (int b = a + 1; b < nums.length; b++) {
                for (int c = b + 1; c < nums.length; c++) {
                    for (int d = c + 1; d < nums.length; d++) {
                        if (nums[a] + nums[b] + nums[c] + nums[d] == target) {
                            List<Integer> r = new ArrayList<>();
                            r.add(nums[a]);
                            r.add(nums[b]);
                            r.add(nums[c]);
                            r.add(nums[d]);
                            r.sort(Comparator.naturalOrder());
                            fourSum.add(r);
                        }
                    }
                }
            }
        }

        return new ArrayList<>(fourSum);
    }
}

/*
Better - Replace fourth element lookup with HashMap
T - O(n^3)
S - O(n)
 */
class Solution2 {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        if (nums.length < 4) {
            return Collections.emptyList();
        }

        HashMap<Integer, Integer> fourthElement = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            fourthElement.put(nums[i], i);
        }

        HashSet<List<Integer>> fourSum = new HashSet<>();

        for (int a = 0; a < nums.length; a++) {
            for (int b = a + 1; b < nums.length; b++) {
                for (int c = b + 1; c < nums.length; c++) {
                    int sum = nums[a] + nums[b] + nums[c];
                    Integer d = fourthElement.get(target - sum);
                    if (d != null && a != d && b != d && c != d) {
                        List<Integer> r = new ArrayList<>();
                        r.add(nums[a]);
                        r.add(nums[b]);
                        r.add(nums[c]);
                        r.add(nums[d]);
                        r.sort(Comparator.naturalOrder());
                        fourSum.add(r);
                    }
                }
            }
        }

        return new ArrayList<>(fourSum);
    }
}

/*
Optimal - Use two pointer approach to find third and fourth element and avoiding HashMap

T - O(n^3)
S - O(1)
 */
class Solution3 {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        if (nums.length < 4) {
            return Collections.emptyList();
        }

        Arrays.sort(nums);
        List<List<Integer>> fourSum = new ArrayList<>();

        for (int a = 0; a < nums.length; a++) {
            if (a > 0 && nums[a] == nums[a - 1]) {
                continue;
            }
            for (int b = a + 1; b < nums.length; b++) {
                if (b > a + 1 && nums[b] == nums[b - 1]) {
                    continue;
                }
                int sum = nums[a] + nums[b];
                int c = b + 1;
                int d = nums.length - 1;
                while (c < d) {
                    if (c > b + 1 & nums[c] == nums[c - 1]) {
                        c++;
                        continue;
                    }
                    if (d < nums.length - 1 && nums[d] == nums[d + 1]) {
                        d--;
                        continue;
                    }
                    int s = sum + nums[c] + nums[d];
                    if (s == target) {
                        List<Integer> r = new ArrayList<>();
                        r.add(nums[a]);
                        r.add(nums[b]);
                        r.add(nums[c]);
                        r.add(nums[d]);
                        fourSum.add(r);
                        c++;
                    } else if (s < target) {
                        c++;
                    } else {
                        d--;
                    }
                }
            }
        }

        return fourSum;
    }
}