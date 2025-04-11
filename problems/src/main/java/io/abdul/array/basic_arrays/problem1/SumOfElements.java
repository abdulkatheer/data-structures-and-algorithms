package io.abdul.array.basic_arrays.problem1;

// https://takeuforward.org/plus/dsa/beginner-problem/basic-arrays/sum-of-array-elements
public class SumOfElements {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.sum(new int[]{1, 2, 3, 4, 5}, 5));
    }
}

class Solution {
    public int sum(int arr[], int n) {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += arr[i];
        }

        return sum;
    }
}

