package io.abdul.binary_search.faqs.problem3;

public class PeakElement {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
        System.out.println(solution.findPeakElement(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 5, 1}));
        System.out.println(solution.findPeakElement(new int[]{1, 2, 1, 3, 5, 6, 4}));
        System.out.println(solution.findPeakElement(new int[]{-2, -1, 3, 4, 5}));
        System.out.println(solution.findPeakElement(new int[]{1, 2, 3, 4, 5, 4, 3, 2, 1}));
        System.out.println(solution.findPeakElement(new int[]{4, 3, 2, 1, 3, 1, 2, 3, 4}));
        System.out.println(solution.findPeakElement(new int[]{1, 3, 1, 3, 1, 3, 1, 3, 1}));
        System.out.println(solution.findPeakElement(new int[]{4,3,2,1,3,1,2,3,4,5,4,3,2,1,4,3}));
    }
}

/*
Brute - Linear
T - O(n)
S - O(1)
 */
class Solution {
    public int findPeakElement(int[] arr) {
        if (arr.length == 1) {
            return 0;
        }
        if (arr[1] < arr[0]) {
            return 0;
        }
        if (arr[arr.length - 2] < arr[arr.length - 1]) {
            return arr.length - 1;
        }

        for (int i = 1; i < arr.length - 1; i++) {
            if (arr[i - 1] < arr[i] && arr[i + 1] < arr[i]) {
                return i;
            }
        }

        return -1;
    }
}

/*
Optimal - Modified Binary Search
T - O(log n)
S - O(1)

Intuition: Upon checking all peak cases, going towards larger element always gives the peak. When both sides are larger, take any route.
 */
class Solution2 {
    public int findPeakElement(int[] arr) {
        int n = arr.length;
        if (n == 1) {
            return 0;
        }

        if (arr[0] > arr[1]) {
            return 0;
        }
        if (arr[n - 1] > arr[n - 2]) {
            return n - 1;
        }

        int low = 1, high = n - 2;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (arr[mid] > arr[mid - 1] && arr[mid] > arr[mid + 1]) {
                return mid;
            } else if (arr[mid - 1] > arr[mid] && arr[mid + 1] < arr[mid]) { // Left is larger
                high = mid - 1;
            } else if (arr[mid + 1] > arr[mid] && arr[mid - 1] < arr[mid]) { // Right is larger
                low = mid + 1;
            } else { // Both are larger, choose any
                low = mid + 1;
            }
        }
        return -1;
    }
}