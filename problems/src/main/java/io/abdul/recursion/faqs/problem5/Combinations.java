package io.abdul.recursion.faqs.problem5;

public class Combinations {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.combinations(1, 1));
        System.out.println(solution.combinations(5, 5));
        System.out.println(solution.combinations(5, 2));
    }
}

class Solution {

    /*
    Read combinations.md to understand why this recursion relation works better.
    C(n,k) = C(n-1,k-1) + C(n-1,k);
    C(n,k) = 1 where k==0 or k==n

    c(n-1, k-1) = Including nth element, we need to select k-1 elements from remaining n-1 elements
    c(n-1, k) = Excluding nth element, we need to select k elements from remaining n-1 elements

    Similar to finding subsets, but here we limit the number elements to be picked
     */
    public int combinations(int n, int k) {
        if (k == 0 || k == n) {
            return 1;
        }

        return combinations(n - 1, k - 1) + combinations(n - 1, k);
    }
}
