package io.abdul.problem21;

// https://leetcode.com/problems/permutation-sequence/solutions/2502436/c-100-fastest-solution-best-approach-with-good-explanation-easy-to-understand-1/

import java.util.ArrayList;
import java.util.List;

/**
 * Navigate to the exact bucket where the k-th element resides
 * Let's say we've 4 buckets, each can hold 6 elements.
 * Where 20th element can fit? (20-1) / 6 = 19/6 = 3 -> result + 1 bucket = 4th bucket
 * Where 24th element can fit? (24-1) / 6 = 23/6 = 3 -> result + 1 bucket = 4th bucket
 * Where 19th element can fit? (19-1) / 6 = 18/6 = 3 -> result + 1 bucket = 4th bucket
 * Where 18th element can fit? (18-1) / 6 = 17/6 = 2 -> result + 1 bucket = 3rd bucket
 * <p>
 * Let's start bucket number from 0 to avoid +1
 * <p>
 * Now in 3rd bucket, where 20th element will sit
 * Convert (20-1) to fit into 0 to 5, modulo helps here
 * 19%6 = 1, so 20th element fits in 1st bucket
 */
public class InspiredSolution1 {
    public String getPermutation(int n, int k) {
        int totalElements = 1;
        List<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            nums.add(i);
            totalElements = totalElements * i;
        }

        StringBuilder builder = new StringBuilder();
        find(k - 1, n, totalElements, builder, nums);
        return builder.toString();
    }

    /**
     * Original -> k=17, n=4
     *
     * k=16,n=4,totalResults=24,""
     * k=4,n=3,totalResults=6,"3"
     * k=0,n=2,totalResults=2,"34"
     * k=0,n=1,totalResults=1,"341"
     * k=0,n=0,totalResults=1,"3412"
     * @param k
     * @param n
     * @param totalResults
     * @param builder
     */
    private static void find(int k, int n, int totalResults, StringBuilder builder, List<Integer> nums) {
        if (n == 0) {
            return;
        }

        // find the bucket
        int bucketSize = totalResults / n;
        int b = k / bucketSize;
        builder.append(nums.get(b));
        nums.remove(b);
        // Fit into next smaller bucket
        find(k % bucketSize, n - 1, bucketSize, builder, nums);
    }

    public static void main(String[] args) {
        InspiredSolution1 solution = new InspiredSolution1();
        System.out.println(solution.getPermutation(4, 17));
        System.out.println(solution.getPermutation(3, 3));
    }
}
