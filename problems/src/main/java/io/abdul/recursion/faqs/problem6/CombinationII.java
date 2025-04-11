package io.abdul.recursion.faqs.problem6;

import java.util.ArrayList;
import java.util.List;

public class CombinationII {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.combinations(9, 1));
        System.out.println(solution.combinations(9, 2));
    }
}

class Solution {
    public List<List<Integer>> combinations(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        combinations(n, k, 1, new ArrayList<>(k), result);
        return result;
    }

    private void combinations(int n, int k, int i, List<Integer> buffer, List<List<Integer>> result) {
        if (buffer.size() == k) { // Consider only k size subsets, stop recursion for that particular i once we've got it
            result.add(new ArrayList<>(buffer));
            return;
        }

        for (int j = i; j <= n; j++) {
            buffer.add(j);
            combinations(n, k, j + 1, buffer, result);
            buffer.remove(buffer.size() - 1);
        }
    }
}