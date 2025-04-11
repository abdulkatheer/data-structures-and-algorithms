package io.abdul.recursion.practice.problem21;

// https://leetcode.com/problems/permutation-sequence/

import java.util.ArrayList;
import java.util.List;

/**
 * Maintain a bit vector kind of and iterate for every non-filled position
 * 1. Maintain a reusable bit vector - for storing unvisited numbers
 * 2. Maintain a reusable stack - for storing permutations incrementally
 * 3. Unset bit and stack data after an iteration to try another option - Backtracking
 * 4. Optimization : Stopping the for loop in every iteration, after expected permutations achieved
 *
 */
// T(n) = n+ (n * T(n-1))

/**
 * T(3) = 3 + (3 * T(2)) -> 3 + (3 * 4) = 15 + 1 (root method call)
 * T(2) = 2 * T(1) -> 2 + (2 * 1) = 4
 * T(1) = 1
 * T(0) = 1
 *
 * From master's theorem
 * Time complexity - O(n * n^n) without optimization
 * Space complexity - O(n + k)
 */
// #tag_recursion
class Solution {
    public String getPermutation(int n, int k) {
        ArrayList<String> permutations = new ArrayList<>();
        permute(new boolean[n], new StringBuilder(), permutations, k);
        return permutations.get(k - 1);
    }

    private static void permute(boolean[] bits, StringBuilder permutation, List<String> permutations, int expected) {
        boolean allFilled = true;
        for (boolean bit : bits) {
            if (!bit) {
                allFilled = false;
                break;
            }
        }

        if (allFilled) {
            permutations.add(permutation.toString());
            return;
        }

        for (int i = 0; i < bits.length; i++) {
            if (!bits[i]) {
                bits[i] = true;
                permutation.append(i + 1);
                permute(bits, permutation, permutations, expected);
                bits[i] = false;
                permutation.deleteCharAt(permutation.length() - 1);
                if (permutations.size() == expected) {
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        //System.out.println(solution.getPermutation(3, 3)); // 9
//        System.out.println(solution.getPermutation(4, 9)); // 26
//        System.out.println(solution.getPermutation(4, 24)); // 65
        System.out.println(solution.getPermutation(3, 6)); // 15
    }
}