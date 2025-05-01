package io.abdul.array.faq_hard.problem6;

// https://takeuforward.org/plus/dsa/arrays/faqs-hard/maximum-product-subarray-in-an-array
public class MaximumProductSubarray {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
        System.out.println(solution.maxProduct(new int[]{4, 5, 3, 7, 1, 2}));
        System.out.println(solution.maxProduct(new int[]{-5, 0, -2}));
        System.out.println(solution.maxProduct(new int[]{1, -2, 3, 4, -4, -3}));
    }
}

/*
Brute - Find all possible products
T - O(n^2)
 */
class Solution {
    public int maxProduct(int[] nums) {
        int maxProduct = Integer.MIN_VALUE;

        for (int i = 0; i < nums.length; i++) {
            int product = 1;
            for (int j = i; j < nums.length; j++) {
                product = product * nums[j];
                if (product > maxProduct) {
                    maxProduct = product;
                }
            }
        }

        return maxProduct;
    }
}

/*
Optimal - Observation and basic math based
T - O(n)
S - O(1)

1) All +ve
2) Even -ve
3) Odd -ve
4) Zeros

All +ve and Even -ve, entire array is the answer
Odd negative, either we need to skip right negative and left negative based of which product is higher
To handle this odd negative case, we do product from prefix and suffix.

Ex1) 1 4 13 4 -> Entire subarray
Ex2) 1 -2 4 5 9 -1 -> Entire subarray
Ex3) 5 -1 2 3 -3 2 -2 7
Now prefix will skill right negative
prefix products -> 5, -5, -10, -30, 90, 180, -360, -xxxx => 180 is the max when we ignore right side negative
suffix products -> 7, -14, -28, 84, 252, 504, -504, -xxxx => 504 is the max when we ignore left side negative
So 504 is the max
Ex4) 5 -1 7 3 0 2 3 0 -3 2 -2 7
Possible candidates -> [5], [7,3] [2,3] [-3,2,-2,7]
Prefix -> [5], [3,3], [-3,2,-2,7]
Suffix -> [7,-2,2,-3], [3,2], [3,7]
 */
class Solution2 {
    public int maxProduct(int[] nums) {
        int maxProduct = Integer.MIN_VALUE;

        int prefixProduct = 1;
        int suffixProduct = 1;

        int n = nums.length;
        for (int i = 0; i < n; i++) {
            prefixProduct *= nums[i];
            suffixProduct *= nums[n - i - 1];

            if (prefixProduct > maxProduct) {
                maxProduct = prefixProduct;
            }
            if (suffixProduct > maxProduct) {
                maxProduct = suffixProduct;
            }
            if (prefixProduct == 0) {
                prefixProduct = 1;
            }
            if (suffixProduct == 0) {
                suffixProduct = 1;
            }
        }

        return maxProduct;
    }
}