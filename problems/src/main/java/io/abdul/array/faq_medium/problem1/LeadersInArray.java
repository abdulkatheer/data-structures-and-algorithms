package io.abdul.array.faq_medium.problem1;

import java.util.ArrayList;

public class LeadersInArray {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.leaders(new int[]{1, 2, 5, 3, 1, 2}));
        System.out.println(solution.leaders(new int[]{-3, 4, 5, 1, -4, -5}));
        System.out.println(solution.leaders(new int[]{-3, 4, 5, 1, -30, -10}));
        System.out.println(solution.leaders(new int[]{198, 393, 946, 655, 978, 781, 472, 239, 639, 738, 705, 8, 355, 457, 69, 152, 37, 74, 390, 659, 238, 132, 122, 739, 687, 992, 699, 258, 684, 903, 556, 903, 330, 321, 413, 600, 197, 696, 512, 671, 429, 644, 908, 952, 714, 608, 178, 346, 182, 77, 134, 956, 313, 582, 160, 217, 291, 907, 924, 151, 541, 850, 271, 533, 705, 258, 716, 835, 307, 426}));
    }
}

class Solution {
    public ArrayList<Integer> leaders(int[] nums) {
        ArrayList<Integer> leaders = new ArrayList<>();
        leaders.add(nums[nums.length - 1]);
        int i = nums.length - 2;

        while (i >= 0) {
            if (nums[i] > leaders.get(leaders.size() - 1)) {
                leaders.add(nums[i]);
            }
            i--;
        }

        int mid = leaders.size() / 2;

        for (int j = 0; j < mid; j++) {
            int temp = leaders.get(leaders.size() - j - 1);
            leaders.set(leaders.size() - j - 1, leaders.get(j));
            leaders.set(j, temp);
        }

        return leaders;
    }
}
