package io.abdul.binary_search.faqs.problem7;

public class SplitArray {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.largestSubarraySumMinimized(new int[]{1, 2, 3, 4, 5}, 3));
        System.out.println(solution.largestSubarraySumMinimized(new int[]{3,5,1}, 3));
        System.out.println(solution.largestSubarraySumMinimized(new int[]{1, 2, 3, 4, 5}, 2));
        System.out.println(solution.largestSubarraySumMinimized(new int[]{1, 2, 3, 4, 5}, 1));
        System.out.println(solution.largestSubarraySumMinimized(new int[]{1, 2, 3, 4, 5}, 4));
        System.out.println(solution.largestSubarraySumMinimized(new int[]{1, 2, 3, 4, 5}, 5));
    }
}

class Solution {
    public int largestSubarraySumMinimized(int[] a, int k) {
        int low = 0;
        int high = 0;
        for (int num : a) {
            low = Math.max(low, num);
            high += num;
        }

        int answer = 0;
        while (low <= high) {
            int mid = (low + high) / 2;

            int subarrays = countSubarrays(a, mid);
            if (subarrays > k) {
                low = mid + 1;
            } else { // find better
                answer = mid;
                high = mid - 1;
            }
        }

        return answer;
    }

    private int countSubarrays(int[] a, int maxSumAllowed) {
        int count = 0;
        int sum = 0;
        for (int num : a) {
            if (sum + num <= maxSumAllowed) {
                sum += num;
            } else {
                sum = num;
                count++;
            }
        }
        if (sum > 0) {
            count++; // last group
        }
        return count;
    }
}
