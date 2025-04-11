package io.abdul.recursion.implementation_problems.problem4;

import java.util.ArrayList;
import java.util.List;

public class BinaryPermutations {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.binaryPermutations(1));
        System.out.println(solution.binaryPermutations(2));
        System.out.println(solution.binaryPermutations(3));
    }
}

/*
Like Parentheses problem, but we can choose any option
 */
class Solution {
    public List<String> binaryPermutations(int n) {
        ArrayList<String> permutations = new ArrayList<>();
        binaryPermutations(n - 1, permutations, new char[n]);
        return permutations;
    }

    private void binaryPermutations(int i, List<String> permutations, char[] binaries) {
        if (i < 0) {
            permutations.add(new String(binaries));
            return;
        }
        binaries[i] = '0';
        binaryPermutations(i - 1, permutations, binaries);

        binaries[i] = '1';
        binaryPermutations(i - 1, permutations, binaries);
    }
}
