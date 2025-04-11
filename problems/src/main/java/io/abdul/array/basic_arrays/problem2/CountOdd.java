package io.abdul.array.basic_arrays.problem2;

// https://takeuforward.org/plus/dsa/beginner-problem/basic-arrays/count-of-odd-numbers-in-array
public class CountOdd {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.countOdd(new int[]{1, 2, 1}, 3));
    }
}

class Solution {
    public int countOdd(int[] arr, int n) {
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] % 2 != 0) {
                count++;
            }
        }

        return count;
    }
}