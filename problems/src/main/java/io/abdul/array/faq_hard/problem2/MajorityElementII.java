package io.abdul.array.faq_hard.problem2;

import java.util.*;

// https://takeuforward.org/plus/dsa/arrays/faqs-hard/majority-element-ii
/*
We can't have more than two elements with >n/3 frequency
 */
public class MajorityElementII {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();
        System.out.println(solution.majorityElementTwo(new int[]{1, 2, 1, 1, 3, 2}));
        System.out.println(solution.majorityElementTwo(new int[]{1, 2, 1, 1, 3, 2, 2}));
        System.out.println(solution.majorityElementTwo(new int[]{1, 2, 1, 1, 3, 2, 2, 3}));
        System.out.println(solution.majorityElementTwo(new int[]{8, 8, 7, 7, 8, 7, 0, 7, 8, 8, 8, -2, 8, 8, 8, 4, 7, 3, 8, 8, -2, 7, 8, 8, -3, 8, 4, 8, 1, 8, 8, 7, 7, 0, 8, 7, 7, 7, 4, 8, 8, -1, 8, 7, 7, -2, 7, 8, 7, 1, 8, 8, 7, 7, -5, 2, 7, 1, 7, 7, 7, 8, 8, -4, 2, 4, 8, 8, 2, 8, 7, 8, -5, -1, 0, 7, 8, 7, -5, 7, 7}));
    }
}

/*
Better - HashMap to avoid inner loop
T - O(n)
S - O(n)
 */
class Solution {
    public List<Integer> majorityElementTwo(int[] nums) {
        HashMap<Integer, Integer> countsByNum = new HashMap<>();
        for (int num : nums) {
            Integer count = countsByNum.getOrDefault(num, 0);
            countsByNum.put(num, count + 1);
        }

        List<Integer> result = new ArrayList<>();
        int expectedCount = nums.length / 3;
        for (Map.Entry<Integer, Integer> countByNumEntry : countsByNum.entrySet()) {
            if (countByNumEntry.getValue() > expectedCount) {
                result.add(countByNumEntry.getKey());
            }
        }

        return result;
    }
}

/*
Better - Sorting to avoid additional space
T - O(n logn)
S - O(1)
 */
class Solution2 {
    public List<Integer> majorityElementTwo(int[] nums) {
        Arrays.sort(nums);

        int count = 1;
        int num = nums[0];
        int e = nums.length / 3;
        List<Integer> result = new ArrayList<>();

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == num) {
                count++;
            } else {
                if (count > e) {
                    result.add(num);
                }
                num = nums[i];
                count = 1;
            }
        }

        if (count > e) { // Handle last element
            result.add(num);
        }

        return result;
    }
}

/*
Optimal - Two popular elements in moving window approach
T - O(n)
S - O(1)
 */
class Solution3 {
    public List<Integer> majorityElementTwo(int[] nums) {

        int num1 = -1;
        int num2 = -1;
        int popularity1 = 0;
        int popularity2 = 0;

        for (int num : nums) {
            if (popularity1 == 0) { // num1 is vacant
                if (popularity2 != 0 && num == num2) {// num2 is not vacant and num matches
                    popularity2++;
                } else { // Fill vacant num1
                    num1 = num;
                    popularity1 = 1;
                }
            } else if (popularity2 == 0) { // num2 is vacant
                if (popularity1 != 0 && num == num1) {// num1 is vacant and num matches
                    popularity1++;
                } else { // Fill vacant num2
                    num2 = num;
                    popularity2 = 1;
                }
            } else { // Two popular elements exist
                if (num1 == num) { // num1 popularity increases
                    popularity1++;
                } else if (num2 == num) { // num2 popularity increases
                    popularity2++;
                } else { // Some other element, so both popularity reduces
                    popularity1--;
                    popularity2--;
                }
            }
        }

        int count1 = 0, count2 = 0;
        for (int num : nums) {
            if (num == num1) {
                count1++;
            } else if (num == num2) {
                count2++;
            }
        }

        List<Integer> result = new ArrayList<>();
        if (count1 > nums.length / 3) {
            result.add(num1);
        }
        if (count2 > nums.length / 3) {
            result.add(num2);
        }

        return result;
    }
}
